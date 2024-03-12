<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Thrillio.IO</title>
</head>

<body style="font-family:Arial;font-size:20px;">

    <div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
		<br><b>
		<a href="" style="font-family:garamond;font-size:34px;margin:0px 0px 0px 10px;color:white;text-decoration: none;">Thrillio.IO</a></b>
		
		<div style="height:25px;background: #DB5227;font-family: Arial;color: white;">
			<b>
			<a href="<%=request.getContextPath()%>/bookmark" style="font-size:16px;color:white;margin-left:1100px;text-decoration:none;">Browse</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">LogOut</a>				
			</b>
		</div>
				
	</div>
	
	<br><br>
	
	   <c:choose>
	      <c:when test="${!empty(books)}">
	         <table>
	         <c:forEach var = "book" items="${books}">
			     <tr>
				   <td>
				     <img src="${book.getImageUrl()}" width="175" height="200">
				   </td>
					    
				   <td style="color:gray;">
				     Title <span style="color: #B13100;">${book.getTitle()}</span>
					 <br><br>
					 By <span style="color: #B13100;">${book.getAuthors()[0]}</span>
					 <br><br>
					 Publisher: <span style="color: #B13100;">${book.getPublisher()}</span>
					 <br><br>
					 Rating: <span style="color: #B13100;">${book.getAmazonRating()}</span>
					 <br><br>
					 Publication Year: <span style="color: #B13100;">${book.getPublicationYear()}</span>
					 <br><br>
					 Genre: <span style="color: #B13100;">${book.getGenre()}</span>
					 <br><br>
					 <a href = "<%=request.getContextPath()%>/bookmark/delete?bid=${book.getId()}" style="font-size:18px;color:#0058A6;font-weight:bold;text-decoration:none">Remove</a>
				   </td>
				  </tr>
				  <tr>
		     	    <td>&nbsp;</td>
		  		  </tr>
		  		 
			   </c:forEach>
	         </table>
	      </c:when>
	      <c:otherwise>
	         <br><br>
       	     <span style="font-size: 24px;color: #333333;margin:400px;">You haven't saved any items yet!</span>
	      </c:otherwise>
	   
	   </c:choose>

</body>

</html>