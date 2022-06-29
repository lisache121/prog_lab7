package commands;

import databaseUtils.UserChecker;
import interaction.ResponseMessage;
import interaction.Status;

import java.sql.SQLException;

public class SignUpCommand extends AbstractCommand{

    private UserChecker userChecker;
    public SignUpCommand(UserChecker userChecker) {
        super("sign_up", "зарегистрировать нового пользователя");
        this.userChecker = userChecker;
    }

    @Override
    public ResponseMessage execute() throws SQLException {
        if (!userChecker.checkUserInData(getArgs().getUser())){
            if (userChecker.addUser(getArgs().getUser())){
                ResponseMessage message = new ResponseMessage("you have successfully signed up");
                message.setStatus(Status.OK);
                return message;
            }
        }
        return new ResponseMessage().error("registration failed, user with this username already exists");
    }
}
