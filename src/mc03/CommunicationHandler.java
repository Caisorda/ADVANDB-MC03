package mc03;

public class CommunicationHandler {

	
	
	
	public CommunicationHandler(){
		
	
		
		
		Thread catcher = new Thread(new reciever(9876, 1024, 1024));
		catcher.start();

		sender man = new sender();

		//man.send("DATA");
		String query ="select (CASE WHEN croptype=1 THEN 'SUGAR CANE'"+
		        "WHEN croptype=2 THEN 'PALAY'"+
		        "WHEN croptype=3 THEN 'CORN'"+
		        "WHEN croptype=4 THEN 'COFFEE'"+
		        "ELSE 'OTHER'"+
				 "END) crop_name, count(hh.id)"+
				 "from hpq_hh hh "+
				 "inner join hpq_crop crop"+
				 " on(crop.hpq_hh_id = hh.id)"+
				 "where croptype is not null "+
				 "group by crop.croptype";

		//man.DoQuery(query);
		
		//man.send("DATA ~ "+query);
		
		System.out.println(man.resultData(query));
		
	}
	
	
	
	
	
	
	
	
}
