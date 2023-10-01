package ru.bot.valera.bot.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.service.handlers.exception.ExceptionHandler;
import ru.bot.valera.bot.to.UpdateTo;

import static ru.bot.valera.bot.bot.MessageSender.sendQueue;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionInterceptor {

    private final ExceptionHandler exceptionHandler;

    @AfterThrowing(pointcut = "execution(* ru.bot.valera.bot.service..*.*(..))", throwing = "ex" )
    public void handle(MessageCounterException ex) {
        UpdateTo updateTO = ex.getUpdateTO();
        updateTO.setErrorMessage(ex.getErrorMessage());
        Content content = exceptionHandler.handle(ex.getUpdateTO());
        sendQueue.add(content);
    }
}
