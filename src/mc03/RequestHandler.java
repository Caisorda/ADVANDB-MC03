package mc03;

public interface RequestHandler {

	LockManager manager = LockManager.getInstance();
	QueryHandler queryHandler = QueryHandler.getInstance();
	TransactionLogger tranLogger = TransactionLogger.getInstance();
	public void interpret(String request);
}
