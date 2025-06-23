package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "D:/uploads"; // Thư mục cố định

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Enumeration<String> paramNames = request.getParameterNames();
        System.out.println("UploadServlet: Received parameters:");
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println("  " + paramName + ": " + paramValue);
        }

        String filePathParam = request.getParameter("filePath");
        System.out.println("UploadServlet: Received filePath parameter: " + filePathParam);

        if (filePathParam == null || filePathParam.isEmpty()) {
            System.out.println("UploadServlet: File path is null or empty");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File path is required");
            return;
        }

        String realPath = UPLOAD_DIR + filePathParam.replace("/uploads/", "/");
        System.out.println("UploadServlet: Constructed real path: " + realPath);

        File file = new File(realPath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("UploadServlet: File does not exist or is not a file at: " + realPath);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found at: " + realPath);
            return;
        }

        System.out.println("UploadServlet: File found, proceeding to serve: " + file.getAbsolutePath());

        String mimeType = getServletContext().getMimeType(filePathParam);
        if (mimeType == null) {
            mimeType = "image/jpeg";
            System.out.println("UploadServlet: Mime type not detected, defaulting to: " + mimeType);
        } else {
            System.out.println("UploadServlet: Detected mime type: " + mimeType);
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("UploadServlet: File streamed successfully, bytes read: " + bytesRead);
        } catch (IOException e) {
            System.out.println("UploadServlet: Error streaming file: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error streaming file: " + e.getMessage());
            return;
        }
        System.out.println("UploadServlet: Request processed successfully");
    }
}