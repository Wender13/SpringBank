// package dev.jr.SpringBank.config;

// import java.io.IOException;
// import javax.servlet.*;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import org.springframework.stereotype.Component;

// @Component
// public class SimpleCorsFilter implements Filter {

//     @Override
//     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//             throws IOException, ServletException {
//         HttpServletResponse response = (HttpServletResponse) res;
//         HttpServletRequest request = (HttpServletRequest) req;

//         // Define os cabeçalhos CORS
//         response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//         response.setHeader("Access-Control-Allow-Credentials", "true");
//         response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//         response.setHeader("Access-Control-Max-Age", "3600");
//         response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

//         // Se for uma requisição OPTIONS (preflight), encerre aqui com status 200
//         if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//             response.setStatus(HttpServletResponse.SC_OK);
//             return;
//         }

//         chain.doFilter(req, res);
//     }

//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {
//         // Método de inicialização, se necessário
//     }

//     @Override
//     public void destroy() {
//         // Método de finalização, se necessário
//     }
// }
