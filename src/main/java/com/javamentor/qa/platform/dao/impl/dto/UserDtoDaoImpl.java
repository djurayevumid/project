package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.dto.answer.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.dto.user.UserSupplierDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserSupplierDto> getUserDtoById(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                    "SELECT new com.javamentor.qa.platform.models.dto.user.UserSupplierDto" +
                       "(u.id, u.email, u.fullName, u.imageLink, u.city, SUM(COALESCE(r.count, 0))) " +
                       "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id WHERE u.id =:id GROUP BY u.id",
                       UserSupplierDto.class)
                    .setParameter("id", id));
    }

    @Override
    public List<BookMarksDto> getBookMarksDtoByUserId(Long id) {
        //noinspection unchecked
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.BookMarksDto" +
                "(bm.question.id," +
                "bm.question.title," +
                "(select count(a.id) from Answer a where a.question.id = bm.question.id)," +
                "(select count(vote.vote) from VoteQuestion vote where vote.question.id = bm.question.id and vote.vote = 'UP_VOTE') - " +
                "(select count(vote.vote) from VoteQuestion vote where vote.question.id = bm.question.id and vote.vote = 'DOWN_VOTE')," +
                "(select count(v.user) from QuestionViewed v where v.question.id = bm.question.id)," +
                "bm.question.persistDateTime)" +
                "from BookMarks bm " +
                "where bm.user.id = :id")
            .setParameter("id", id)
            .getResultList();
    }

    @Override
    public List<UserProfileAnswerDto> getUserAnswersDtoById(long id) {
        return entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.answer.UserProfileAnswerDto" +
                        "(a.id, a.htmlBody, ((select COUNT(v.id) from VoteAnswer v WHERE v.answer.id = a.id AND v.vote = 'UP_VOTE') - (select COUNT(v.id) " +
                        "from VoteAnswer v WHERE v.answer.id = a.id AND v.vote = 'DOWN_VOTE' ))," +
                        "a.question.id, a.persistDateTime)" +
                        "FROM Answer a WHERE a.user.id =:id",
                UserProfileAnswerDto.class)
                .setParameter("id", id)
                .getResultList();

    }

    @Override
    public List<UserProfileQuestionDto> getDeletedUserProfileQuestionDtos(Long id) {
        //noinspection JpaQlInspection,unchecked
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto(" +
            "q.id," +
            "q.title," +
            "(select count(a.id) from Answer a where a.question.id = q.id)," +
            "q.persistDateTime) " +
            "from Question q " +
            "where q.user.id = :id " +
            "and q.isDeleted = true")
            .setParameter("id", id)
            .getResultList();
    }

    @Override
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByUserId(long id) {
        return entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto" +
                                "(q.id, q.title, COUNT(answer.id), q.persistDateTime) FROM Question q " +
                                "LEFT JOIN Answer answer ON q.id = answer.question.id " +
                                "WHERE q.user.id =:id " +
                                "GROUP BY q.id",
                        UserProfileQuestionDto.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<UserDto> getTop10Users() {

        final LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);

        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.user.UserDto" +
                                "(u.id, u.nickname, u.city, SUM(COALESCE(r.count, 0)) AS rep, u.imageLink) " +
                                "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id " +
                                "INNER JOIN Answer a ON u.id=a.user.id AND a.persistDateTime > :weekAgo " +
                                "LEFT JOIN VoteAnswer voa ON a.id=voa.answer.id " +
                                "GROUP BY u.id " +
                                "ORDER BY COUNT(DISTINCT a.id) DESC, " +
                                "SUM(CASE WHEN voa.vote = 'UP_VOTE' THEN 1 WHEN voa.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END) DESC, " +
                                "u.nickname"
                        , UserDto.class)
                .setParameter("weekAgo", weekAgo)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<UserDto> getTop10UsersByAnswersLastMonth() {
        final LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.user.UserDto" +
                                "(u.id, u.nickname, u.city, SUM(COALESCE(r.count, 0)) AS rep, u.imageLink) " +
                                "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id " +
                                "INNER JOIN Answer a ON u.id=a.user.id AND a.persistDateTime > :monthAgo " +
                                "LEFT JOIN VoteAnswer voa ON a.id=voa.answer.id " +
                                "GROUP BY u.id " +
                                "ORDER BY COUNT(DISTINCT a.id) DESC, " +
                                "SUM(CASE WHEN voa.vote = 'UP_VOTE' THEN 1 WHEN voa.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END) DESC, " +
                                "u.nickname"
                        , UserDto.class)
                .setParameter("monthAgo", monthAgo)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<UserDto> getTop10UsersByAnswersLastYear() {
        final LocalDateTime yearAgo = LocalDateTime.now().minusYears(1);
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.user.UserDto" +
                                "(u.id, u.nickname, u.city, SUM(COALESCE(r.count, 0)) AS rep, u.imageLink) " +
                                "FROM User u LEFT JOIN Reputation r ON u.id = r.author.id " +
                                "INNER JOIN Answer a ON u.id=a.user.id AND a.persistDateTime > :yearAgo " +
                                "LEFT JOIN VoteAnswer voa ON a.id=voa.answer.id " +
                                "GROUP BY u.id " +
                                "ORDER BY COUNT(DISTINCT a.id) DESC, " +
                                "SUM(CASE WHEN voa.vote = 'UP_VOTE' THEN 1 WHEN voa.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END) DESC, " +
                                "u.nickname"
                        , UserDto.class)
                .setParameter("yearAgo", yearAgo)
                .setMaxResults(10)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Long getCountAnswersByWeek(Long id) {
        final LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        return (Long) entityManager.createQuery(
            "SELECT COUNT(a.id) FROM Answer a " +
                    "WHERE a.user.id= :userId " +
                    "AND a.persistDateTime > :weekAgo AND a.isDeleted=false " +
                    "GROUP BY a.user.id")
                .setParameter("weekAgo", weekAgo)
                .setParameter("userId", id)
                .getResultStream().findFirst().orElse(0L);
    }

}
