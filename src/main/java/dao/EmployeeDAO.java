package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import entity.Employee;

@Repository("empDAO")
public class EmployeeDAO {
	@Autowired
	@Qualifier("jt")
	private JdbcTemplate jt;
	
	/**
	 * JdbcTemplate会将底层异常统一转换成运行时异常然后抛出
	 * @param emp
	 */
	public void save(Employee emp) {
		String sql="INSERT INTO t_emp "+
				   "VALUES(t_emp_seq.nextval,?,?,?) ";
		Object[] args= {emp.getName(),emp.getAge(),emp.getSalary()};
		jt.update(sql, args);
	}
	
	public List<Employee> findAll() {
		String sql="SELECT * FROM t_emp ";
		return jt.query(sql, new EmpRowMapper());
	}
	
	public Employee findById(int id) {
		String sql="SELECT * FROM t_emp "+
				   "WHERE id=? ";
		Object[] args={id};
		Employee emp=null;
		try {
			emp=jt.queryForObject(sql, args,new EmpRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return emp;
	}
	
	public void update(Employee emp) {
		String sql="UPDATE t_emp "+
				   "SET name=?,age=?,salary=? "+
				   "WHERE id=? ";
		Object[] args= {emp.getName(),emp.getAge(),emp.getSalary(),emp.getId()};
		jt.update(sql,args);
	}
	
	public void delete(int id) {
		String sql="DELETE t_emp "+
				   "WHERE id=? ";
		Object[] args= {id};
		jt.update(sql,args);
	}
	//告诉JdbcTemplate如何处理结果集ResultSet
	class EmpRowMapper implements RowMapper<Employee>{
		/*
		 * index:正在被处理的记录的下标
		 * 在该方法里面，告诉JdbcTemplate如何将记录转换为一个实体对象
		 */
		public Employee mapRow(
				ResultSet rs, int index) 
				throws SQLException {
			Employee emp=new Employee();
			emp.setId(rs.getInt("id"));
			emp.setName(rs.getString("name"));
			emp.setAge(rs.getInt("age"));
			emp.setSalary(rs.getDouble("salary"));
			return emp;
		}
		
	}
}
