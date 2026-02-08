package services;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DaoCode;
import model.Activity;
import model.Profile;
import model.Txn;
import util.Connectiondb;
public class GetAllDataService {
	DaoCode d1= new DaoCode();
    public Profile get_profile_by_id(Long id) throws SQLException{
    	Connection conn = Connectiondb.getConnection();
    	return d1.get_profile_by_id(conn,id);
    }
    
    public List<Activity> get_activites_by_id(Long id) throws SQLException{
    	Connection conn = Connectiondb.getConnection();
    	return d1.get_activities_by_id(conn,id);
    }
    
    public List<Txn> get_transactions_by_id(Connection conn,Long id) throws SQLException{
    	return d1.get_transactions_by_id(conn,id);
    }
}
