package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.question.UserProfileQuestionDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<QuestionDto> getQuestionDtoByIdAndUserAuthId(long id, long userId) {
        Optional<QuestionDto> o = SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                "SELECT new com.javamentor.qa.platform.models.dto.question.QuestionDto" +
                        "(q.id, " +
                        "q.title, " +
                        "q.user.id , " +
                        "q.user.fullName, " +
                        "q.user.imageLink, " +
                        "q.description, " +
                        "(SELECT COUNT(qv.id) FROM QuestionViewed qv WHERE qv.question.id = :id) as count_views, " +
                        "COUNT(answer.id), " +
                        "(SELECT COUNT(up.vote) FROM VoteQuestion up WHERE up.vote = 'UP_VOTE' AND up.question.id = :id) - " +
                        "(SELECT COUNT(up.vote) FROM VoteQuestion up WHERE up.vote = 'DOWN_VOTE' AND up.question.id = :id), " +
                        "COUNT(r.count), " +
                        "q.persistDateTime, " +
                        "q.lastUpdateDateTime," +
                        "(SELECT v.vote FROM VoteQuestion v WHERE v.question.id = q.id AND v.user.id = :userId)," +
                        "EXISTS(SELECT b.id FROM BookMarks b WHERE b.question.id = q.id AND b.user.id = :userId), " +
                        "EXISTS(SELECT vans.id from VoteAnswer vans inner join User us on vans.user.id = us.id " +
                        "inner join Answer ans on vans.answer.id = ans.id inner join Question q on q.id = ans.question.id " +
                        "where us.id = :userId " +
                        "and ans.question.id = :id " +
                        ")) " +
                        "FROM Question q " +
                        "LEFT JOIN Reputation r ON q.user.id = r.author.id " +
                        "LEFT JOIN Answer answer ON q.user.id = answer.user.id " +
                        "WHERE q.id =:id " +
                        "GROUP BY q.id, q.user.fullName, q.user.imageLink",
                QuestionDto.class)
                .setParameter("id", id)
                .setParameter("userId", userId));
        return o;


    }

}
