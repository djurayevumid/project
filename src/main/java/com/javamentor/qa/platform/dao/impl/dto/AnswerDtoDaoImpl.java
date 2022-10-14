package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.models.dto.answer.AnswerDto;
import com.javamentor.qa.platform.models.dto.answer.AnswerUserDto;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AnswerDtoDaoImpl implements AnswerDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnswerDto> getListAnswersOfQuestionId(Long id) {
        return entityManager.createQuery("SELECT DISTINCT new com.javamentor.qa.platform.models.dto.answer.AnswerDto( " +
                            "answer.id, " +
                            "answer.user.id, " +
                            "(SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE answer.user.id = r.author.id) As count_reputation, " +
                            "answer.question.id, " +
                            "answer.htmlBody, " +
                            "answer.persistDateTime, " +
                            "answer.isHelpful, " +
                            "answer.dateAcceptTime, " +
                            "((SELECT count (up.vote) FROM VoteAnswer up WHERE up.vote = 'UP_VOTE' AND answer.id = up.answer.id) - " +
                                "(SELECT count (down.vote) FROM VoteAnswer down WHERE down.vote = 'DOWN_VOTE' AND answer.id = down.answer.id)) As count_vote, " +
                            "answer.user.imageLink, " +
                            "answer.user.nickname, " +
                            "CASE WHEN ((SELECT count (up.vote) FROM VoteAnswer up WHERE up.vote = 'UP_VOTE' AND answer.id = up.answer.id) - " +
                                "(SELECT count (down.vote) FROM VoteAnswer down WHERE down.vote = 'DOWN_VOTE' AND answer.id = down.answer.id)) " +
                                "> 0 THEN 'UP_VOTE' ELSE 'DOWN_VOTE' END) " +
                                "FROM Answer answer " +
                                    "LEFT JOIN VoteAnswer voteAnswer ON answer.id = voteAnswer.answer.id " +
                                    "LEFT JOIN Reputation reputation ON answer.id = reputation.answer.id " +
                                        "WHERE answer.question.id = :questionId AND answer.isDeleted = false " +
                                            "ORDER BY answer.isHelpful DESC, count_vote DESC, count_reputation DESC",
                    AnswerDto.class)
            .setParameter("questionId", id)
            .getResultList();
    }

    @Override
    public List<AnswerUserDto> getAnswerUserPerWeek(Long id) {
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        return entityManager.createQuery("SELECT new com.javamentor.qa.platform.models.dto.answer.AnswerUserDto" +
                "(answ.id,answ.question.id ,count(voa.answer.id), answ.htmlBody )" +
                        "From Answer answ " +
                        "  JOIN VoteAnswer voa ON answ.id = voa.answer.id "+
                        " WHERE answ.user.id = :userId and answ.persistDateTime > : weekAgo" +
                        " GROUP BY answ.id, answ.htmlBody, answ.question.id" +
                        " ORDER BY COUNT(DISTINCT answ.id) DESC, " +
                        "SUM(CASE WHEN voa.vote = 'UP_VOTE' THEN 1 WHEN voa.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END), " +
                        "answ.id"
                        ,AnswerUserDto.class)
                .setParameter("userId", id)
                .setParameter("weekAgo", weekAgo)
                .getResultList();

    }
}
