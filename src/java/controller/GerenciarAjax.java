 
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(name = "GerenciarAjax", urlPatterns = {"/gerenciar_ajax.do"})
public class GerenciarAjax extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarAjax</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarAjax at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        PrintWriter out = response.getWriter();
        String start = request.getParameter("start");//ex: 0
        String length = request.getParameter("length");// ex: 10,25
        String draw = request.getParameter("draw");
        String pesquisa = request.getParameter("search[value]");
        String pesquisaregex = request.getParameter("search[regex]");
        String order = request.getParameter("order[i][column]");
        
        
        String json = ""
        
      +"  {"
    +"\" draw \": "+draw+" ,"
    +"\"recordsTotal\": 12,"
    +"\"recordsFiltered\": 12,"
    +"\"data\": ["
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"
                
            +" {"
            + "\"id\": \" start:"+start+" \","
            + " \"last_name\": \" length:"+length+" \","
            + " \"position\": \" "+order+" \","
            + "  \"office\": \" teste \","
            + "\"start_date\": \" teste \","
            + " \"salary\": \"$2,875\""
            +" },"
                
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"    
            
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"    
                
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"
                
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"
                
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"    
            
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"    
             
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"
                
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"
                
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" },"    
            
            +" {"
            + "\"id\": \"Angelica\","
            + " \"last_name\": \"Ramos\","
            + " \"position\": \"System Architect\","
            + "  \"office\": \"London\","
            + "\"start_date\": \"9th Oct 09\","
            + " \"salary\": \"$2,875\""
            +" }"    
                 
                
                
  + " ]"
+"}";
        
        
        response.getWriter().write(json);
        
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
