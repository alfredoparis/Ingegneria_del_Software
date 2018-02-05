<%--
    Document   : singleRobot
    Created on : 29-dic-2017, 17.01.47
    Author     : AD-TEAM
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <link rel="stylesheet" href="bootstrap.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="stile_single_robot.css">
  <link rel="stylesheet" type="text/css" href="style.css">

  <style>
  @import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);
  </style>
    <head>


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <title>Single Robot</title>
    </head>
    <body>
	 <div class="forms">
      <div class="row">
          <a href="Dashboard_Home.jsp" class="alignright"> <i class="fa fa-home fa-2x"></i> Home</a> <br>

        <div class="col-sm-4 col-md-3 col-lg-3 col-md-offset-3 col-sm-offset-2">
                       <div id="imaginary_container">
                          <form action="singleRobot.jsp" metod="get">
                           <div class="input-group stylish-input-group input-append">

                               <input type="number" class="form-control"  name="robot" placeholder="Ricerca ID Robot" min="1" max="90000" required oninvalid="this.setCustomValidity('Inserire ID Robot')" oninput="setCustomValidity('') " >
                               <span class="input-group-addon">
                                   <button type="submit">
                                       <span class="glyphicon glyphicon-search"></span>
                                   </button>
                                   </form>
                               </span>

                           </div>
                       </div>
                   </div>
                 <div class="col-sm-4 col-md-3">
                       <div id="imaginary_container">
                          <form action="singleCluster.jsp" method="get">
                           <div class="input-group stylish-input-group input-append">
                               <input type="number" name="cluster" placeholder="Ricerca ID Cluster" min="1" max="100" oninput="setCustomValidity('')" required oninvalid="this.setCustomValidity('Inserire IDCluster')" class="form-control"   >
                               <span class="input-group-addon">
                                   <button type="submit">
                                       <span class="glyphicon glyphicon-search"></span>
                                   </button>
                                 </form>
                               </span>
                           </div>
                       </div>
                   </div>


		</div>
      </div>
        <%
         String idRobot=request.getParameter("robot");
         int id=Integer.parseInt(idRobot);
         String user="root";
         String pass="root";
         String url="jdbc:mysql://localhost/RobotDB5";
         Connection c;
         Class.forName("com.mysql.jdbc.Driver");
         c=DriverManager.getConnection(url,user,pass);
         String cl="select * from robot where idrobot=(?)";
         String cl2="select s1,s2,s3,s4,s5,s6,s7,idCluster,time from fullstate where idrobot=(?) ORDER BY idRobot ASC";
         PreparedStatement s=c.prepareStatement(cl);
         PreparedStatement s1=c.prepareStatement(cl2);
         s1.setInt(1,id);
	
		
         ResultSet r2=s1.executeQuery();
         if(r2.first()){
           int idCluster=r2.getInt("idCluster");
       out.print("<p class=\"text-center\">"+"ID Robot: "+"<b>"+idRobot+"</b>"+"<b>" + " --- " + "</b>"+"ID Cluster: "+"<b>"+idCluster+"</b>"+"</h4>"+"</p>");%>

		

        <div class="table-title">

        </div>
        <table class="table-fill">
          <thead>
            <tr>
              <th class="text-left">ID Sensore:</th>
              <th class="text-left">Valore:</th>

            </tr>
          </thead>
          <tbody class="table-hover">
        <%

         s.setInt(1, id);

         int sgn1=r2.getInt(1);
         int sgn2=r2.getInt(2);
         int sgn3=r2.getInt(3);
         int sgn4=r2.getInt(4);
         int sgn5=r2.getInt(5);
         int sgn6=r2.getInt(6);
         int sgn7=r2.getInt(7);
         Date times=r2.getTimestamp("time");

         ResultSet rs =s.executeQuery();
         while(rs.next()){
             int idcluster=rs.getInt(1);
             int idrobo=rs.getInt(2);
             int irrobo=rs.getInt(3);
             out.print("<tr>"+"<td class=\"text-right\">"+"s1"+"</td>"+"<td class=\"text-right\">"+sgn1+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"s2"+"</td>"+"<td class=\"text-right\">"+sgn2+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"s3"+"</td>"+"<td class=\"text-right\">"+sgn3+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"s4"+"</td>"+"<td class=\"text-right\">"+sgn4+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"s5"+"</td>"+"<td class=\"text-right\">"+sgn5+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"s6"+"</td>"+"<td class=\"text-right\">"+sgn6+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"s7"+"</td>"+"<td class=\"text-right\">"+sgn7+"</td>"+"</tr>");
			 out.print("<tr>"+"<td class=\"text-right\">"+"IR"+"</td>"+"<td class=\"text-right\">"+irrobo+"%"+"</td>"+"</tr>");
             out.print("<tr>"+"<td class=\"text-right\">"+"Ultimo Agg."+"</td>"+"<td class=\"text-right\">"+"<b>"+times+"</b>"+"</td>"+"</tr>");
			


         }
         }else{
            out.print("ERRORE.L'ID Robot INSERITO NON CORRISPONDE A NESSUN ROBOT. <a href=\"Dashboard_Home.jsp\">TORNA ALLA HOME</a>");
        }
        %>
      </tbody>
      </table>


    </body>
    <footer>

  </footer>
</html>
