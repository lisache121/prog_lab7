package interaction;

public class ResponseMessage implements Response, Runnable{
    private String msg;
    private Status status;
    public ResponseMessage(){
        msg = "";
        status = Status.OK;
    }

    /**
     * Clear message
     */
    public ResponseMessage clear(){
        msg = "";
        return this;
    }


    public ResponseMessage error(Object str){
        msg = str.toString();
        setStatus(Status.ERROR);
        return this;
    }


    public ResponseMessage setStatus(Status st){
        status = st;
        return this;
    }


    public Status getStatus(){
        return status;
    }


    @Override
    public String toString(){
        switch (getStatus()){
            case ERROR:
                return "Err: " + getMessage();
            default:
                return getMessage();
        }
    }
    public ResponseMessage(String msg) {
        this.msg = msg;
        setStatus(Status.OK);
    }

    @Override
    public String getMessage() {
        return msg;
    }
    public ResponseMessage info(Object str){
        msg = str.toString();// + "\n";
        return this;
    }


    @Override
    public void run() {

    }
}
