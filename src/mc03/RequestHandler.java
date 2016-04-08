package mc03;

public interface RequestHandler {

	LockManager manager = LockManager.getInstance();
	QueryHandler queryHandler = QueryHandler.getInstance();
	TransactionLogger tranLogger = TransactionLogger.getInstance();
	RecoveryHandler recoveryHandler = RecoveryHandler.getInstance();
	public void interpret(String request);
}
