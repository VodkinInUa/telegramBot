package ua.gov.publicfinance.telegrambot.domain.model.entities.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.gov.publicfinance.telegrambot.domain.model.entities.ChatID;


@Repository
public class ChatIdDaoImpl implements ChatIdDao {

    private static final Logger logger = LoggerFactory.getLogger(ChatIdDaoImpl.class);

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addChatId(ChatID chatID) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(chatID);
        logger.info("New ChatId added to base. -> details: " + chatID);
    }


    @Override
    public ChatID getChatIdById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        ChatID chatID = session.load(ChatID.class, id);
        logger.info("Book successfully load. Book details: " + chatID);

        return chatID;
    }


}
