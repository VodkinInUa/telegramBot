package ua.gov.publicfinance.telegrambot.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ua.gov.publicfinance.telegrambot.domain.model.agrigates.Chat;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> { 
	
	public Optional<Chat> findByChatId(@Param("chatId") long chatId);	
}