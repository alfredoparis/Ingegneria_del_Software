<%--
    Document   : singleCluster
    Created on : 29-dic-2017, 16.25.10
    Author     : Alfredo
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <link rel="stylesheet" href="bootstrap.css">
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <style>

        @import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);

        .robo {border-style: solid;
           border-radius: 3px 3px 3px 3px;
        -moz-border-radius: 3px 3px 3px 3px;
        -webkit-border-radius: 3px 3px 3px 3px;
        border: 1px solid #000000;

         margin-right: 3px;
         margin-top:1%;
         margin-left: 3%;

         -webkit-box-shadow: 0px 7px 24px -6px rgba(0,0,0,0.75);
        -moz-box-shadow: 0px 7px 24px -6px rgba(0,0,0,0.75);
        box-shadow: 0px 7px 24px -6px rgba(0,0,0,0.75);


         }
         h4 {
             text-align: center;
         }

        .stylish-input-group .input-group-addon{
            background: white !important;
        }
        .stylish-input-group .form-control{
            border-right:0;
          box-shadow:0 0 0;
          border-color:#ccc;
        }
        .stylish-input-group button{
            border:0;
            background:transparent;
        }
        h3 {
            text-align: center;
        }

        #imaginary_container{

            margin-top:5%;
          margin-left:10px;
        }

        .alignright {
            float: right;
            margin-right : 20px;
            color: black; }

            body {
              background-color: white;
              font-family: "Roboto", helvetica, arial, sans-serif;
              font-size: 20px;
              font-weight: 100;
              text-rendering: optimizeLegibility;
            }

            .texto{
              font-family: "Roboto", helvetica, arial, sans-serif;
              font-size: 15px;
              font-weight: 100;
              text-rendering: optimizeLegibility;

             text-align: right;
             font-weight: bold;

            }
        </style>
          <link rel="stylesheet" href="style.css">

        <link rel="stylesheet" href="style.css">
        <title>Single Cluster</title>


  <a href="Dashboard_Home.jsp" class="alignright"> <i class="fa fa-home fa-2x"></i> Home</a> <br>
        <%
            int x=0;
            if(request.getParameter("ir")==null){
                x=40;
            }else{
                x= Integer.parseInt(request.getParameter("ir"));
            }
            int id=Integer.parseInt(request.getParameter("cluster"));
             String user="root";
             String pass="root";
             String url="jdbc:mysql://localhost/RobotDB";
             Connection c;
             Class.forName("com.mysql.jdbc.Driver");
             c=DriverManager.getConnection(url,user,pass);
            String e="select * from cluster where idCluster=(?) ORDER BY idCluster ASC";
            PreparedStatement s1=c.prepareStatement(e);
            s1.setInt(1, id);
            ResultSet r =s1.executeQuery();
            out.print("<div class=\"header\">");
            out.print("<div class=\"row\">");
            if(r.next()){

            /*out.print("<a href=\"Dashboard_Home.jsp\" style=\"margin-left: 1380px; margin-top: 40px;\"><i class=\"fa fa-home fa-2x\"></i> Home</a><br>");*/

            String ur="singleCluster.jsp";
            out.print("<a href=\"Dashboard_Home.jsp\" class=\"alignright\">"+"<i class=\"fa fa-home fa-2x\">"+"</i>"+"Home"+"</a>"+"<br>");
            out.print("<div class=\"col-sm-2 col-md-3 col-lg-3 col-md-offset-1\">");
            out.print("<div id=\"imaginary_container\">");
            out.print("<form action=\""+ur+"\" method=\"get\">");
            out.print("<div class=\"input-group stylish-input-group input-append\">");

        %>
            <input type="number" class="form-control"  name="ir" placeholder="Valore IR" min="0" max="100" oninput="setCustomValidity('')" required oninvalid="this.setCustomValidity('Inserire valore IR')">
            <span class="input-group-addon">
                <button type="submit">
                    <span class="glyphicon glyphicon-check"></span>
                </button>
                <%
                out.print("<input type=\"hidden\" name=\"cluster\" value=\""+id+"\">");
                %>
                </form>
            </span>

        </div>
    </div>
 </div>

 <div class="col-sm-4 col-md-3">
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





        <%

            out.print("</div>");
            String cl="select * from robot where idCluster=(?) ORDER BY idRobot ASC";
            PreparedStatement s=c.prepareStatement(cl);
            s.setInt(1, id);
            ResultSet rs =s.executeQuery();
            out.print("<div class=\"row\">");
            out.print("<div class=\"box\">");
            out.print("<center><text class=\"texto\">"+" Impostazione IR attuale: "+"<i>"+x+"%</i>"+" --- ID Cluster Attuale: "+"<i>"+id+"</i> --- Valore IR Cluster: "+"<i>"+r.getString(2)+"</i></text></center>");
            while(rs.next()){
                int irrobot=rs.getInt("IRrobot");
                int idRobot=rs.getInt(2);
                String urll="singleRobot.jsp?robot="+""+idRobot;

                if(irrobot<=x){
                    out.print("<a href=\""+urll+"\">"+"<div class=\"col-xs-2 col-sm-2 col-md-1 col-lg-1 robo\" style=\"background-color: green\"><h4>"+"R: "+ idRobot+"</h4><center><b><i>"+irrobot+"%</i></b></center></div>"+"</a>");
                }else{
                    out.print("<a href=\""+urll+"\">"+"<div class=\"col-xs-2 col-sm-2 col-md-1 col-lg-1 robo\" style=\"background-color: red\"><h4>"+"R: "+ idRobot+"</h4><center><b><i>"+irrobot+"%</i></b></center></div>"+"</a>");
                }
            }
            }else{
            out.print("ERRORE.L'ID Cluster immesso non esiste.<a href=\"Dashboard_Home.jsp\">Riprovare</a>");
            }
            out.print("</div>");

        %>
    </div>
        <footer>
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-4">
                <p>@AD-TEAM</p>
            </div>
                <div class="col-md-4">
                </div>
            </div>
        </footer>
        </div>
</body>
