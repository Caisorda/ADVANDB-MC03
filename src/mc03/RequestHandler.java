package mc03;

public interface RequestHandler {

	LockManager manager = LockManager.getInstance();
	QueryHandler queryHandler = QueryHandler.getInstance();
	
	public void interpret(String request);
}
