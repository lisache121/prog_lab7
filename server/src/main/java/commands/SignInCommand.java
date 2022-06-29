package commands;

import databaseUtils.UserChecker;
import interaction.ResponseMessage;
import interaction.Status;

import java.sql.SQLException;

public class SignInCommand extends AbstractCommand{
//тут аргументы это пара будет


    private final UserChecker userChecker;

    public SignInCommand(UserChecker userChecker) {
        super("sign_in", "авторизация пользователя");
        this.userChecker = userChecker;
    }

    public ResponseMessage execute() throws SQLException {
        if (userChecker.checkUserInData(getArgs().getUser())){
            ResponseMessage message = new ResponseMessage("you have successfully logged in");
            message.setStatus(Status.OK);
            return message;
        }
        if (userChecker.matchUsername(getArgs().getUser())){
            ResponseMessage message = new ResponseMessage();
            message.error("your password is incorrect");
            return message;
        }
        return new ResponseMessage().error("authorization failed. your password or username is incorrect");
    }
}

