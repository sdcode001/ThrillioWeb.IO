<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SignUp</title>
</head>
<body>
	<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">Thrill.IO</a></b>  
		
		<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
			<b>	
			<a href="<%=request.getContextPath()%>/auth/login" style="font-size:16px;color:white;margin-left:1150px;text-decoration:none;">LogIn</a>	
			</b>
		</div>
		        
	</div>
	<br><br>
	<form method="POST" action="<%=request.getContextPath()%>/auth/register">
      <fieldset>
	    <legend>Sign Up</legend>	    
	    <table>
	        <tr>
	    		<td><label>First Name:</label></td>
        		<td>
        			<input type="text" name="first_name"><br>        			
        		</td>
        	</tr>
	        <tr>
	    		<td><label>Last Name:</label></td>
        		<td>
        			<input type="text" name="last_name"><br>        			
        		</td>
        	</tr>
	    	<tr>
	    		<td><label>Email:</label></td>
        		<td>
        			<input type="text" name="email"><br>        			
        		</td>
        	</tr>
        	<tr>
        		<td><label>Password:</label></td>
        		<td>
        			<input type="password" name="password"><br>
        		</td>        
        	</tr>
        	
        	<tr>
        	     <label for="gender">Choose Gender:</label>
        	     <select id="gender" name="gender">
					  <option value="0">Male</option>
					  <option value="1">Female</option>
					  <option value="2">Transgender</option>
				 </select>
        	</tr>
        	
        	<tr>
        	     <label for="usertype">Choose User-Type:</label>
        	     <select id="usertype" name="usertype">
					  <option value="0">User</option>
					  <option value="1">Editor</option>
					  <option value="2">Chief Editor</option>
				 </select>
        	</tr>
        	
        	<tr>
        		<td>&nbsp;</td>
        		<td><input type="submit" name="submitLoginForm" value="Register"></td>
        	</tr>
        </table>
	  </fieldset>      
    </form>
</body>
</html>