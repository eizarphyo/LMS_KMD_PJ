package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.StudentModel;

public class StudentController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(StudentModel student) {
		String query = "INSERT INTO lib.student (stu_id,stu_name,grade) VALUES (?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, student.getStuId());
			ps.setString(2, student.getStuName());
			ps.setString(3, student.getGrade());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(StudentModel student)  {
		String query = "UPDATE lib.student SET stu_name=?, grade=? WHERE stu_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, student.getStuName());
			ps.setString(2, student.getGrade());
			ps.setString(3, student.getStuId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(StudentModel student) {
		String query = "DELETE FROM lib.student WHERE stu_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, student.getStuId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<StudentModel> getAllStudents() {
		String query = "SELECT * FROM lib.student ORDER BY stu_id DESC";
		List<StudentModel> students = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				StudentModel student = new StudentModel();

				student.setStuId(rs.getString("stu_id"));
				student.setStuName(rs.getString("stu_name"));
				student.setGrade(rs.getString("grade"));

				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public StudentModel getOneStudentById(StudentModel data) {
		String query = "SELECT * FROM lib.student WHERE stu_id=?";

		StudentModel student = new StudentModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getStuId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				student.setStuId(rs.getString("stu_id"));
				student.setStuName(rs.getString("stu_name"));
				student.setGrade(rs.getString("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public List<StudentModel> searchByName(StudentModel data) {
		String query = "SELECT * FROM lib.student WHERE stu_name LIKE ? ORDER BY stu_id DESC";
		List<StudentModel> students = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getStuId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				StudentModel student = new StudentModel();

				student.setStuId(rs.getString("stu_id"));
				student.setStuName(rs.getString("stu_name"));
				student.setGrade(rs.getString("grade"));
				
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public boolean hasDuplicateName(StudentModel student) {
		String query = "SELECT * FROM lib.student WHERE stu_name=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, student.getStuName());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
