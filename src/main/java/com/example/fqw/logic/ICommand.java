package com.example.fqw.logic;

import java.time.LocalDateTime;

public interface ICommand {
    CommandTypes getCommandType();

    LocalDateTime getTimeStart();

    StatusCommand getStatusCommand();

    LocalDateTime getTimeFinish();

}
