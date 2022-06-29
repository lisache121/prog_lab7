package utils;


import data.Dragon;

/**
 * class comparator to sort and compare
 */
public class Comparator implements java.util.Comparator<Dragon>{

    @Override
    public int compare(Dragon o1, Dragon o2) {
        return o1.getName().length() - o2.getName().length();

    }

    /**
     *
     * @param o1 instance of Dragon
     * @param o2 instance of Dragon
     * @return comparison result
     */
    public static int compareByDescription(Dragon o1, Dragon o2) {
        return o1.getDescription().length() - o2.getDescription().length();
    }
}




































//                Callable<CommandRequest> taskToRead = this::readCommandReq;
//                Future<CommandRequest> requestFuture = readTheRequest.submit(taskToRead);
//                while (!requestFuture.isDone()) {
//                }
//
//                Callable<ResponseMessage> taskToExec = () -> {
//                    try {
//                        return execute(requestFuture.get());   //invoke the command
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                };
//                //executeRequest.invoke(ForkJoinTask.adapt(taskToExec));
//
//
//                Future<ResponseMessage> resp = executeRequest.submit(taskToExec);
//                while (!resp.isDone()){
//                    answerGet.wait();
//                }
//
//                Runnable sendResponse = () -> {
//
//                    try {
//                        this.server.sendResponse(resp.get());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//
//
//                };
//                answerGet.execute(sendResponse);
