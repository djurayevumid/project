package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.dto.authentication.AuthenticationRequestDto;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.UserChatPin;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.chat.MessageStarService;
import com.javamentor.qa.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.qa.platform.service.abstracts.model.chat.ChatService;
import com.javamentor.qa.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.qa.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.qa.platform.service.abstracts.model.question.*;
import com.javamentor.qa.platform.service.abstracts.model.user.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.user.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.user.UserChatPinService;
import com.javamentor.qa.platform.service.abstracts.model.user.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TestDataInitService {

    private final BCryptPasswordEncoder passwordEncoder;

    //Amount of test data
    private final static int usersNum = 60;//first 50 with permanent email and password and has role USER, others with random parameters
    private final static int rolesNum = 7;
    private final static int answersNum = 10;
    private final static int questionsNum = 50;
    private final static int tagsNum = 50;
    private final static int reputationsNum = 60;

    private final List<AuthenticationRequestDto> permanentUserParameters = new ArrayList<>();

    //static fields for random values
    private static final Character[] alphabet = "abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c -> (char) c).toArray(Character[]::new);

    private static final String[] firstNames = new String[]{"Harry", "Ross", "Bruce", "Cook", "Carolyn", "Morgan", "Albert", "Walker", "Randy", "Reed", "Larry", "Barnes", "Lois", "Wilson", "Jesse", "Campbell", "Ernest", "Rogers", "Theresa", "Patterson", "Henry", "Simmons", "Michelle", "Perry", "Frank", "Butler", "Shirley"};

    private static final String[] middleNames = new String[]{"Brooks", "Rachel", "Edwards", "Christopher", "Perez", "Thomas", "Baker", "Sara", "Moore", "Chris", "Bailey", "Roger", "Johnson", "Marilyn", "Thompson", "Anthony", "Evans", "Julie", "Hall", "Paula", "Phillips", "Annie", "Hernandez", "Dorothy", "Murphy", "Alice", "Howard"};

    private static final String[] lastNames = new String[]{"Ruth", "Jackson", "Debra", "Allen", "Gerald", "Harris", "Raymond", "Carter", "Jacqueline", "Torres", "Joseph", "Nelson", "Carlos", "Sanchez", "Ralph", "Clark", "Jean", "Alexander", "Stephen", "Roberts", "Eric", "Long", "Amanda", "Scott", "Teresa", "Diaz", "Wanda", "Thomas"};

    private static final String[] domains = new String[]{"mail", "email", "gmail", "vk", "msn", "yandex", "yahoo", "edu.spbstu", "swebhosting"};

    private static final String[] domainCodes = new String[]{"ru", "com", "biz", "info", "net", "su", "org"};

    private static final String[] cities = new String[]{"Saint-Petersburg", "Moscow", "Vologda", "Volgograd", "Murmansk", "Vladivostok", "Novgorod", "Tula"};

    private static final String[] abouts = new String[]{"student", "dentist", "engineer", "social worker", "nurse", "doctor"};

    private static final String[] roles = new String[]{"ROLE_ADMIN", "ROLE_USER", "ROLE_ANONYMOUS", "ROLE_GUEST", "ROLE_UNDEFINED", "ROLE_MAIN"};

    private static final String[] randomWords = new String[]{"teach", "about", "you", "may", "back", "going to", "live", "destination", "tomorrow", "big", "date", "I", "walk", "theatre", "queue", "window", "package", "run", "into", "for", "over", "apple", "dark", "order", "seller", "headphone", "break", "buy", "new", "old", "nice", "great", "cat", "dog", "fish", "java", "amazing", "bad", "util", "useful", "dangerous", "abstract", "kind", "jump", "rare", "unique", "legendary", "common", "double", "creative", "boring", "something", "clear"};

//    private static final String[] htmlTags = new String[]{
//            "div", "span", "h1", "button", "b", "strong", "sup", "sub"};

    public TestDataInitService(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleService roleService, AnswerService answerService, QuestionService questionService, TagService tagService, ReputationService reputationService, TrackedTagService trackedTagService, IgnoredTagService ignoredTagService, BookMarksService bookMarksService, VoteAnswerService voteAnswerService, VoteQuestionService voteQuestionService, RelatedTagService relatedTagService, GroupChatService groupChatService, SingleChatService singleChatService, MessageService messageService, QuestionViewedService questionViewedService, ChatService chatService, MessageStarService messageStarService, UserChatPinService userChatPinService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.tagService = tagService;
        this.reputationService = reputationService;
        this.trackedTagService = trackedTagService;
        this.ignoredTagService = ignoredTagService;
        this.bookMarksService = bookMarksService;
        this.voteAnswerService = voteAnswerService;
        this.voteQuestionService = voteQuestionService;
        this.relatedTagService = relatedTagService;
        this.groupChatService = groupChatService;
        this.singleChatService = singleChatService;
        this.messageService = messageService;
        this.questionViewedService = questionViewedService;
        this.chatService = chatService;
        this.messageStarService = messageStarService;
        this.userChatPinService = userChatPinService;
    }

    private final UserService userService;
    private final RoleService roleService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final TagService tagService;
    private final ReputationService reputationService;
    private final TrackedTagService trackedTagService;
    private final IgnoredTagService ignoredTagService;
    private final BookMarksService bookMarksService;
    private final VoteAnswerService voteAnswerService;
    private final VoteQuestionService voteQuestionService;
    private final RelatedTagService relatedTagService;
    private final GroupChatService groupChatService;
    private final SingleChatService singleChatService;
    private final MessageService messageService;
    private final QuestionViewedService questionViewedService;
    private final ChatService chatService;
    private final MessageStarService messageStarService;
    private final UserChatPinService userChatPinService;


    //fill related tables user_entity and role with test data
    public void fillTableWithTestData() {
        addRandomRoles();
        addRandomUsersPermanentEmailPassword();
        addRandomTags();
        addRandomQuestions();
        addRandomAnswers();
        addRandomReputation();
        addRandomTrackedTagsToUser();
        addRandomIgnoredTagsToUser();
        addRandomBookmarks();
        addRandomVotes();
        addRandomRelatedTags();
        addRandomChatsAndMessages();
        addRandomQuestionViewed();
        addRandomMessageStars();
        addRandomUserChatPins();
    }

    private void addRandomReputation() {
        List<Answer> answerList = answerService.getAll();
        //Set<Reputation> reputations = new HashSet<>();
        for (int i = 0; i < reputationsNum; i++) {
            int count = getRandInt(0, 1000);
            ReputationType type = ReputationType.values()[getRandInt(0, 3)];
            Reputation reputation = new Reputation();
            reputation.setCount(count);
            reputation.setType(type);
            reputation.setAuthor(userService.getById(i + 1L).orElse(null));
            reputation.setSender(userService.getById((long) getRandInt(1, usersNum)).orElse(null));
            if ((type == ReputationType.Answer || type == ReputationType.VoteAnswer)) {
                reputation.setAnswer(answerService.getById((long) getRandInt(1, answerList.size())).orElse(null));
            } else {
                reputation.setQuestion(questionService.getById((long) getRandInt(1, questionsNum)).orElse(null));
            }
            reputationService.persist(reputation);
            //reputations.add(reputation);
        }
        //reputationService.persistAll(reputations);
    }

    private void addRandomAnswers() {
        List<Answer> answers = new ArrayList<>();
        List<User> existingUsers = userService.getAll();
        List<Question> existingQuestions = questionService.getAll();
        for (Question question : existingQuestions) {
            int answersAmount = getRandInt(0, 10) + 1;
            for (int i = 0; i < answersAmount; i++) {
                String htmlBody = "<" + "h5" + ">" + getRand(randomWords) + " " + getRand(randomWords) + " " + getRand(randomWords) + "</" + "h5" + ">";
                LocalDateTime dateAcceptTime = LocalDateTime.now();
                Answer answer = new Answer(null, null, htmlBody, getRandInt(0, 2) == 1, getRandInt(0, 2) == 1, getRandInt(0, 2) == 1);
                answer.setDateAcceptTime(dateAcceptTime);
                answer.setUser(existingUsers.get(getRandInt(0, existingUsers.size())));
                answer.setQuestion(question);
                if (i % getRandInt(3, 5) == 0) {
                    answer.setEditModerator(existingUsers.get(getRandInt(1, existingUsers.size())));
                }
                answers.add(answer);
            }
        }
        answerService.persistAll(answers);
    }

    private void addRandomQuestions() {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < questionsNum; i++) {
            String title = getRand(randomWords);
            StringBuilder description = new StringBuilder();
            for (int j = 0; j < getRandInt(3, 15); j++) {
                description.append(getRand(randomWords)).append(" ");
            }
            questions.add(new Question(null, title, description.toString(), null, null, null, null, i % 3 == 0, null, null, null, null));
        }

        List<User> existingUsers = userService.getAll();
        List<Tag> existingTags = tagService.getAll();
        for (Question question : questions) {
            question.setUser(existingUsers.get(getRandInt(0, existingUsers.size())));
            int tagAmount = getRandInt(0, 5) + 1;
            List<Tag> tagsToInsert = new ArrayList<>();
            for (int i = 0; i < tagAmount; i++) {
                // если вытаскивается такой же тэг, рандомно ищется другой
                Tag tag;
                do {
                    tag = existingTags.get(getRandInt(0, existingTags.size()));
                } while (tagsToInsert.contains(tag));
                tagsToInsert.add(tag);
            }
            question.setTags(tagsToInsert);
        }

        // если остались незадействованные тэги, ищем их и добавляем в рандомный вопрос
        for (Tag tag : existingTags) {
            int tagCount = 0;
            for (Question question : questions) {
                if (question.getTags().contains(tag)) {
                    tagCount += 1;
                }
            }
            if (tagCount == 0) {
                // если вытаскивается вопрос с 5 тэгами, рандомно ищется другой
                Question question;
                do {
                    question = questions.get(getRandInt(0, questions.size()));
                } while (question.getTags().size() == 5);
                List<Tag> questionTags = question.getTags();
                questionTags.add(tag);
                question.setTags(questionTags);
            }
        }
        questionService.persistAll(questions);
    }

    private void addRandomTags() {
        List<Tag> tags = new ArrayList<>();
        Set<String> tagNames = new HashSet<>();
        while (tagNames.size() <= tagsNum) {
            String name = getRand(randomWords);
            tagNames.add(name);
        }
        List<String> tagNamesList = new ArrayList<>(tagNames);
        for (int i = 0; i < tagsNum; i++) {
            StringBuilder description = new StringBuilder();
            for (int j = 0; j < getRandInt(3, 15); j++) {
                description.append(getRand(randomWords)).append(" ");
            }
            tags.add(new Tag(null, tagNamesList.get(i), description.toString(), null, null));
        }
        tagService.persistAll(tags);
    }

    //генерируем TrackedTags для всех пользователей, кроме первого
    private void addRandomTrackedTagsToUser() {
        List<TrackedTag> trackedTags = new ArrayList<>();
        List<User> userList = userService.getAll();
        List<Tag> tagList = tagService.getAll();
        for (int i = 1; i < usersNum; i++) {
            for (int j = 0; j < getRandInt(0, 4); j++) {
                trackedTags.add(new TrackedTag(tagList.get(getRandInt(0, 9)), userList.get(i)));
            }
        }
        trackedTagService.persistAll(trackedTags);
    }

    //генерируем IgnoredTags для всех пользователей, кроме первого
    private void addRandomIgnoredTagsToUser() {
        List<IgnoredTag> ignoredTags = new ArrayList<>();
        List<User> userList = userService.getAll();
        List<Tag> tagList = tagService.getAll();
        for (int i = 1; i < usersNum; i++) {
            for (int j = 0; j < getRandInt(0, 4); j++) {
                ignoredTags.add(new IgnoredTag(tagList.get(getRandInt(0, 9)), userList.get(i)));
            }
        }
        ignoredTagService.persistAll(ignoredTags);
    }

    private void addRandomUsersPermanentEmailPassword() {
        fillPermanentUserParameters();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < usersNum; i++) {
            String email = (i < permanentUserParameters.size()) ? permanentUserParameters.get(i).getUsername() : getRand(firstNames).toLowerCase() + "@" + getRand(domains) + "." + getRand(domainCodes);
            String fullName = getRand(firstNames) + " " + getRand(middleNames) + " " + getRand(lastNames);
            String password = (i < permanentUserParameters.size()) ? permanentUserParameters.get(i).getPassword() : getRandStr(4, 20);
            String city = getRand(cities);
            String linkSite = "https://" + getRandStr(10, 50);
            String linkGithub = "https://" + getRandStr(5, 30);
            String linkVk = "https://vk.com/" + getRandStr(1, 30);
            String about = getRand(abouts);
            String imageLink = getRandStr(10, 100);
            String nickname = fullName.substring(0, 3);

            users.add(new User(email, passwordEncoder.encode(password), fullName, city, linkSite, linkGithub, linkVk, about, imageLink, nickname));
        }

        List<Role> existingRoles = roleService.getAll();
        for (int i = 0; i < usersNum; i++) {
            if ((i < permanentUserParameters.size())) {
                users.get(i).setRole(existingRoles.get(0));
            } else {
                users.get(i).setRole(existingRoles.get(getRandInt(0, existingRoles.size())));
            }
        }


        userService.persistAll(users);
    }

    private void addRandomBookmarks() {
        List<User> userList = userService.getAll();
        List<Question> questionList = questionService.getAll();
        for (int i = 1; i < usersNum; i++) {
            int userId = getRandInt(0, 60);
            int questionId = getRandInt(0, 10);
            // не persistAll так как проверяем уникальность закладок в DB
            if (!bookMarksService.isBookmarkExist((long) questionId + 1, (long) userId + 1)) {
                BookMarks bookMarks = new BookMarks(userList.get(userId), questionList.get(questionId));
                bookMarksService.persist(bookMarks);
            }
        }
    }

    private void fillPermanentUserParameters() {
        permanentUserParameters.add(new AuthenticationRequestDto("user", "user", false));
        permanentUserParameters.add(new AuthenticationRequestDto("test", "test", false));
        permanentUserParameters.add(new AuthenticationRequestDto("commonUser", "common", false));
        permanentUserParameters.add(new AuthenticationRequestDto("user@mail.ru", "somePass", false));
        for (int i = 1; i <= 46; i++) {
            permanentUserParameters.add(new AuthenticationRequestDto("user" + i, "user" + i, false));
        }
    }

    private void addRandomRoles() {
        Set<Role> testRoles = new HashSet<>();
        while (testRoles.size() < rolesNum - 1) {
            testRoles.add(new Role(getRand(roles)));
        }
        roleService.persistAll(testRoles);
    }

    private void addRandomVotes() {
        List<User> userList = userService.getAll();
        List<Answer> answerList = answerService.getAll();
        Map<Integer, Integer> answerVotesCount = new HashMap<>();
        Map<Integer, Integer> questionVotesCount = new HashMap<>();
        for (int i = 0; i < userList.size(); i++) {
            for (int j = 0; j < getRandInt(1, answerList.size() * 10 / userList.size()); j++) {
                // проверка что у ответа меньше 10 голосов
                int randomAnswerId;
                do {
                    randomAnswerId = getRandInt(0, answerList.size()) + 1;
                    if (!answerVotesCount.containsKey(randomAnswerId)) {
                        answerVotesCount.put(randomAnswerId, 0);
                    }
                } while ((answerVotesCount.get(randomAnswerId) == 10) || (!voteAnswerService.isUserNonVoted((long) randomAnswerId, (long) (i + 1))));
                answerVotesCount.merge(randomAnswerId, 1, Integer::sum);
                if (getRandInt(0, 2) == 1) {
                    voteAnswerService.vote((long) randomAnswerId, userList.get(i), VoteType.UP_VOTE);
                } else {
                    voteAnswerService.vote((long) randomAnswerId, userList.get(i), VoteType.DOWN_VOTE);
                }

            }

            for (int j = 0; j < getRandInt(1, questionsNum * 10 / userList.size()); j++) {
                // проверка что у вопроса меньше 10 голосов
                int randomQuestionId;
                do {
                    randomQuestionId = getRandInt(0, questionsNum) + 1;
                    if (!questionVotesCount.containsKey(randomQuestionId)) {
                        questionVotesCount.put(randomQuestionId, 0);
                    }
                } while ((questionVotesCount.get(randomQuestionId) == 10) || (!voteQuestionService.checkIfVoteQuestionDoesNotExist((long) randomQuestionId, (long) (i + 1))));
                questionVotesCount.merge(randomQuestionId, 1, Integer::sum);
                if (getRandInt(0, 2) == 1) {
                    voteQuestionService.voteAndGetCountVoteQuestionFotThisQuestion((long) randomQuestionId, VoteType.UP_VOTE, userList.get(i));
                } else {
                    voteQuestionService.voteAndGetCountVoteQuestionFotThisQuestion((long) randomQuestionId, VoteType.DOWN_VOTE, userList.get(i));
                }
            }
        }
    }

    private void addRandomRelatedTags() {
        List<Tag> tagList = tagService.getAll();
        List<RelatedTag> relatedTags = new ArrayList<>();
        Map<Integer, List<Integer>> relatedTagMap = new HashMap<>();

        for (int i = 0; i < tagsNum; i++) {
            int relatedTagAmount = getRandInt(0, 4);
            if (relatedTagAmount != 0) {
                relatedTagMap.put(i, new ArrayList<>());
            }
            for (int j = 0; j < relatedTagAmount; j++) {
                int randomTag;
                List<Integer> parentTag;
                do {
                    randomTag = getRandInt(0, tagsNum);
                    parentTag = new ArrayList<>();
                    parentTag.add(randomTag);
                } while (checkChildrenTags(parentTag, relatedTagMap, i, randomTag));
                relatedTagMap.get(i).add(randomTag);
                relatedTags.add(new RelatedTag(null, tagList.get(i), tagList.get(randomTag)));
            }
        }

        relatedTagService.persistAll(relatedTags);
    }

    private void addRandomChatsAndMessages() {
        List<User> list = userService.getAll();
        GroupChat groupChat = new GroupChat();
        groupChat.getChat().setTitle(getRand(randomWords));
        Set<User> userSet = new HashSet<>(list);
        groupChat.setUsers(userSet);
        groupChat.setGlobal(true);
        groupChatService.persist(groupChat);

        List<SingleChat> singleChatList = new ArrayList<>();
        Map<Integer, List<Integer>> chatMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (!chatMap.containsKey(i)) {
                chatMap.put(i, new ArrayList<>());
            }
            while (chatMap.get(i).size() < 3) {
                SingleChat singleChat = new SingleChat();
                singleChat.getChat().setTitle(getRand(randomWords));
                singleChat.setUserOne(list.get(i));
                int randomUser;
                do {
                    randomUser = getRandInt(0, list.size());
                } while (chatMap.get(i).contains(randomUser) || (chatMap.containsKey(randomUser) && chatMap.get(randomUser).contains(i)));
                singleChat.setUserTwo(list.get(randomUser));
                singleChat.setDelByUserOne(ThreadLocalRandom.current().nextBoolean());
                singleChat.setDelByUserTwo(ThreadLocalRandom.current().nextBoolean());
                singleChatList.add(singleChat);
                chatMap.get(i).add(randomUser);
            }
        }
        singleChatService.persistAll(singleChatList);

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messageList.add(new Message(getRand(randomWords), getRand(groupChat.getUsers().toArray(new User[0])), groupChat.getChat()));
        }

        for (SingleChat singleChat : singleChatList) {
            if (getRandInt(0, 2) == 1) {
                for (int i = 0; i < getRandInt(0, 5) + 1; i++) {
                    User userWithMessage;
                    if (getRandInt(0, 2) == 1) {
                        userWithMessage = singleChat.getUserOne();
                    } else {
                        userWithMessage = singleChat.getUserTwo();
                    }
                    messageList.add(new Message(getRand(randomWords), userWithMessage, singleChat.getChat()));
                }
            }
        }
        messageService.persistAll(messageList);
    }

    private void addRandomQuestionViewed() {
        List<QuestionViewed> questionViewedList = new ArrayList<>();
        List<User> userList = userService.getAll();
        List<Question> questionList = questionService.getAll();

        for (int i = 0; i < questionsNum; i++) {
            int userId = getRandInt(0, 60);
            int questionId = getRandInt(0, 50);

            for (QuestionViewed qW : questionViewedList) {
                if (qW.getQuestion().getId() == questionId) {
                    questionId = getRandInt(0, 50);
                }
            }

            QuestionViewed questionViewed = new QuestionViewed();
            questionViewed.setUser(userList.get(userId));
            questionViewed.setQuestion(questionList.get(questionId));
            questionViewedList.add(questionViewed);
        }
        questionViewedService.persistAll(questionViewedList);
    }

    private void addRandomMessageStars() {
        List<Chat> chatList = chatService.getAll();
        List<User> userList = userService.getAll();
        List<MessageStar> messageStarList = new ArrayList<>();
        for (User user : userList) {
            int amountOfStarredMessages = getRandInt(0, 4);
            if (amountOfStarredMessages != 0) {
                List<Message> currentMessageList = new ArrayList<>();
                for (Chat chat : chatList) {
                    if (chat.getChatType() == ChatType.GROUP) {
                        if (groupChatService.ifUserInGroupChat(chat.getId(), user.getId())) {
                            currentMessageList.addAll(messageService.getMessagesFromChat(chat.getId()));
                        }
                    } else {
                        if (singleChatService.ifUserInSingleChat(user.getId(), chat.getId())) {
                            currentMessageList.addAll(messageService.getMessagesFromChat(chat.getId()));
                        }
                    }
                }
                List<Message> messageStarMessagesList = new ArrayList<>();
                for (int i = 0; i < amountOfStarredMessages; i++) {
                    MessageStar messageStar = new MessageStar();
                    messageStar.setUser(user);
                    Message randomMessage = currentMessageList.get(getRandInt(0, currentMessageList.size()));
                    if (messageStarMessagesList.contains(randomMessage)) {
                        i--;
                    } else {
                        messageStar.setMessage(randomMessage);
                        messageStarList.add(messageStar);
                        messageStarMessagesList.add(randomMessage);
                    }
                }
            }
        }
        messageStarService.persistAll(messageStarList);
    }

    private void addRandomUserChatPins() {
        List<User> userList = userService.getAll();
        List<Chat> chatList = chatService.getAll();
        List<UserChatPin> userChatPins = new ArrayList<>();

        for (User user : userList) {
            for (Chat chat : chatList) {
                UserChatPin userChatPinGroup = new UserChatPin();
                UserChatPin userChatPinSingle = new UserChatPin();
                if (groupChatService.ifUserInGroupChat(user.getId(), chat.getId())) {
                    userChatPinGroup.setChat(chat);
                    userChatPinGroup.setUser(user);
                    userChatPins.add(userChatPinGroup);
                }
                if (singleChatService.ifUserInSingleChat(user.getId(), chat.getId())) {
                    userChatPinSingle.setChat(chat);
                    userChatPinSingle.setUser(user);
                    userChatPins.add(userChatPinSingle);
                }
            }
        }
        userChatPinService.persistAll(userChatPins);
    }

    private static boolean checkChildrenTags(List<Integer> parents, Map<Integer, List<Integer>> relatedTagMap, Integer childTag, Integer checkingTag) {
        int j = 0;
        for (Integer parent : parents) {
            if (!relatedTagMap.containsKey(parent)) {
                continue;
            }
            List<Integer> childrenList = relatedTagMap.get(parent);
            if (childrenList.contains(childTag) || Objects.equals(checkingTag, childTag)) {
                return true;
            }
            if (checkChildrenTags(childrenList, relatedTagMap, childTag, checkingTag)) {
                j += 1;
            }
        }
        return j != 0;
    }


    private static int getRandInt(int lowBound, int upperBound) {
        return ThreadLocalRandom.current().nextInt(lowBound, upperBound);
    }

    private static <T> T getRand(T[] array) {
        return array[ThreadLocalRandom.current().nextInt(0, array.length)];
    }

    private static String getRandStr(int lowBound, int upperBound) {
        StringBuilder stringBuilder = new StringBuilder();
        int strLength = getRandInt(lowBound, upperBound);
        for (int i = 0; i < strLength; i++) {
            stringBuilder.append(getRand(alphabet));
        }
        return stringBuilder.toString();
    }
}
