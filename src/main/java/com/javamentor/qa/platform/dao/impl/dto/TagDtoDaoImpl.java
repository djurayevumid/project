package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TagDtoDaoImpl implements TagDtoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagDto> getIgnoredTagsByUserId(Long userId) {
        return em.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagDto(tag.id, tag.name, tag.description)" +
                        "FROM IgnoredTag ign INNER JOIN ign.user LEFT JOIN ign.ignoredTag tag WHERE ign.user.id = :userId", TagDto.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<TagDto> getTrackedTagsByUserId(Long userId) {
        return em.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagDto(tag.id, tag.name, tag.description)" +
                        "FROM TrackedTag tracked INNER JOIN tracked.user LEFT JOIN tracked.trackedTag tag WHERE tracked.user.id = :userId", TagDto.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<RelatedTagsDto> getRelatedTagsDto() {
        return em.createQuery
                        ("SELECT new com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto" +
                                "(t.id, t.name, SUM(t.questions.size)) " +
                                "FROM Tag t INNER JOIN Question q ON t.id = q.id " +
                                "GROUP BY t.id " +
                                "ORDER BY SUM(t.questions.size) DESC", RelatedTagsDto.class)
                .setMaxResults(10).getResultList();
    }

    @Override
    public List<TagDto> getTagsByLetters(String letters) {
        return em.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagDto(tag.id, tag.name, tag.description)" +
                        "FROM Tag tag WHERE tag.name LIKE :letters", TagDto.class)
                .setParameter("letters", MatchMode.ANYWHERE.toMatchString(letters))
                .setMaxResults(6).getResultList();
    }

    @Override
    public Map<Long, List<TagDto>> getMapTagsByQuestionIds(List<Long> questionIds){
        List<Tuple> tags = em.createQuery("SELECT t.id as tag_id, t.name as tag_name, t.description as tag_description," +
                                " q.id as question_id From Tag t JOIN t.questions q WHERE q.id in :ids", Tuple.class)
                .setParameter("ids", questionIds)
                .getResultList();

        Map<Long, List<TagDto>> tagsMap = new HashMap<>();
        tags.forEach(tuple -> tagsMap.computeIfAbsent(tuple.get("question_id", Long.class), id -> new ArrayList<>())
                .add(new TagDto(tuple.get("tag_id", Long.class), tuple.get("tag_name", String.class), tuple.get("tag_description", String.class))));
        return tagsMap;
    }

    @Override
    public List<TagDto> getTagDtoListByQuestionId(Long id) {
        return em.createQuery("SELECT new com.javamentor.qa.platform.models.dto.tag.TagDto" +
                "(tag.id, tag.name, tag.description)" +
                "FROM Tag tag where :id IN (select tt.id from tag.questions tt)", TagDto.class).setParameter("id", id).getResultList();
    }

    @Override
    public Map<Long, List<TagDto>> getMapTagsByUserIds(List<Long> userIds) {
        List<Tuple> tags = em.createQuery("SELECT t.id as tag_id, t.name as tag_name, t.description as tag_description," +
                        " q.id as user_id From Tag t JOIN t.questions q WHERE q.user.id in :ids", Tuple.class)
                .setParameter("ids", userIds)
                .getResultList();

        Map<Long, List<TagDto>> tagsMap = new HashMap<>();
        tags.forEach(tuple -> tagsMap.computeIfAbsent(tuple.get("user_id", Long.class), id -> new ArrayList<>())
                .add(new TagDto(tuple.get("tag_id", Long.class), tuple.get("tag_name", String.class), tuple.get("tag_description", String.class))));
        return tagsMap;
    }

    @Override
    public Boolean checkIfConfuseIgnoredAndTrackedTags(Long userId, List<Long> ignoredTags, List<Long> trackedTags) {
        return (Boolean) em.createQuery("select case when exists" +
                        " (select tr from TrackedTag  tr where  tr.user.id=:userId and tr.trackedTag.id in(:TrackedId) ) or " +
                        "exists (select  ig from IgnoredTag  ig  where ig.user.id=:userId and ig.ignoredTag.id in(:IgnoredId) ) " +
                        "then true else false end from TrackedTag ,IgnoredTag ")
                .setParameter("IgnoredId", ignoredTags)
                .setParameter("TrackedId", trackedTags)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public Map<Long, List<TagDto>> getMapTop3TagsByUsersIds(List<Long> usersIds) {

        List<Tuple> tags = em.createQuery("SELECT t.id as tagId, t.name as tagName, t.description as tagDescription, " +
                        "a.user.id as userId " +
                        "FROM Answer a, Question q " +
                        "INNER JOIN q.tags t ON a.question.id=q.id AND a.user.id IN :usersIds " +
                        "GROUP BY tagId, tagName, tagDescription, userId " +
                        "ORDER BY userId, COUNT (t.id) DESC", Tuple.class)
                .setParameter("usersIds", usersIds)
                .getResultList();

        Map<Long, List<TagDto>> tagsMap = new HashMap<>();

        tags.forEach(tuple -> tagsMap.computeIfAbsent(tuple.get("userId", Long.class), id -> new ArrayList<>())
                .add(new TagDto(tuple.get("tagId", Long.class), tuple.get("tagName", String.class), tuple.get("tagDescription", String.class))));

        tagsMap.entrySet().forEach(entry -> entry.setValue(entry.getValue().stream().limit(3L).collect(Collectors.toList())));

        return tagsMap;
    }
}

