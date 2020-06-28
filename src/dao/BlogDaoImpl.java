package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Blog;
import utility.ConnectionManager;

public class BlogDaoImpl implements BlogDaoInterface{

	
	@Override
	public void insertBlog(Blog blog) throws SQLException, Exception {
		ConnectionManager con = new ConnectionManager();
		System.out.println(blog.getBlogId()+" "+blog.getBlogTitle()+" "+blog.getBlogDescription()+" "+blog.getPostedOn());
		
		String sql = "insert into blog values(?,?,?,?)";
		PreparedStatement st = con.getConnection().prepareStatement(sql);
		st.setInt(1,blog.getBlogId());
		st.setString(2, blog.getBlogTitle());
		st.setString(3, blog.getBlogDescription());
		
		st.setDate(4,Date.valueOf(blog.getPostedOn()));
		st.executeUpdate();
		st.close();
		
	}

	@Override
	public List<Blog> selectAllBlogs() throws Exception {
		List<Blog> list = new ArrayList<Blog>();
		
		Blog blog = null;
		ConnectionManager cm=new ConnectionManager();
		Connection con=cm.getConnection();
		
		Statement stmt=con.createStatement();
		String sql="SELECT * FROM blog";
		
		ResultSet rs=stmt.executeQuery(sql);	
		
		while(rs.next()) {
			blog = new Blog();
			blog.setBlogId(rs.getInt("id"));
			blog.setBlogTitle(rs.getString("title"));
			blog.setBlogDescription(rs.getString("description"));
			java.time.LocalDate localDate = rs.getDate("posted_On").toLocalDate();
			blog.setPostedOn(localDate);
			list.add(blog);
		}
		
		rs.close();
		stmt.close();
		con.close();
		return list;
	}

	@Override
	public Blog selectBlog(int blogid) throws Exception {
		Blog blog=new Blog();
		ConnectionManager cm=new ConnectionManager();
		Connection con=cm.getConnection();
		Statement stmt=null;
		stmt=con.createStatement();
		String sql="select * FROM BLOG WHERE ID="+blogid;
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()){
	         blog.setBlogId(rs.getInt("id"));
	         blog.setBlogDescription(rs.getString("description"));
	         blog.setBlogTitle(rs.getString("title"));
	         java.time.LocalDate localDate = rs.getDate("posted_On").toLocalDate();
	         blog.setPostedOn(localDate);
	    }
	    rs.close();
		stmt.close();
		con.close();
	    return blog;
	}

	@Override
	public boolean deleteBlog(int id) throws Exception {
		ConnectionManager cm=new ConnectionManager();
		Connection con=cm.getConnection();
		String sql = "delete from blog where id="+id;
		Statement st=con.createStatement();
		if(st.execute(sql)) {
			st.close(); 
			return true;
		}
		else {
			st.close();
			return false;
		}
	}

	@Override
	public boolean updateBlog(Blog blog) throws SQLException, Exception {
		String sqlQuery = "UPDATE blog SET title = ?, description= ?, posted_On = ? WHERE id = ?";
		ConnectionManager cm=new ConnectionManager();
		Connection con=cm.getConnection();
		PreparedStatement stmt = con.prepareStatement(sqlQuery);
		stmt.setString(1, blog.getBlogTitle());
	    stmt.setString(2, blog.getBlogDescription());
	    stmt.setDate(3,Date.valueOf(blog.getPostedOn()));
	    stmt.setInt(4,blog.getBlogId());
	    stmt.executeUpdate();
	    
	    stmt.close();
	    con.close();
	    
		return false;
	}
	
} 