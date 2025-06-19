<%@ page import="DAO.TutorRequestDAO.TutorRequest" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Yêu cầu trở thành gia sư</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2>Danh sách yêu cầu trở thành gia sư</h2>

    <%
        String success = request.getParameter("success");
        String error = request.getParameter("error");
        if (success != null) {
    %>
    <div class="alert alert-success"><%= success %></div>
    <% } else if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <table class="table table-bordered table-hover mt-4">
        <thead>
        <tr>
            <th>Tên</th>
            <th>Email</th>
            <th>Ngày sinh</th>
            <th>CCCD</th>
            <th>Ngân hàng</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<TutorRequest> requests = (List<TutorRequest>) request.getAttribute("requests");
            for (TutorRequest r : requests) {
        %>
        <tr>
            <td><%= r.getName() %></td>
            <td><%= r.getEmail() %></td>
            <td><%= r.getBirth() %></td>
            <td><%= r.getIdCardNumber() %></td>
            <td><%= r.getBankName() %></td>
            <td>
                <!-- Chi tiết dạng collapsible -->
                <details>
                    <summary class="text-primary">Xem</summary>
                    <div>
                        <p><strong>SDT:</strong> <%= r.getPhone() %></p>
                        <p><strong>Địa chỉ:</strong> <%= r.getAddress() %></p>
                        <p><strong>Chuyên môn:</strong> <%= r.getSpecialization() %></p>
                        <p><strong>Mô tả:</strong> <%= r.getDescription() %></p>
                        <form action="tutor-request-action" method="post" class="d-inline">
                            <input type="hidden" name="requestId" value="<%= r.getId() %>">
                            <input type="hidden" name="action" value="approve">
                            <button type="submit" class="btn btn-success btn-sm">Duyệt</button>
                        </form>
                        <form action="tutor-request-action" method="post" class="d-inline ms-2">
                            <input type="hidden" name="requestId" value="<%= r.getId() %>">
                            <input type="hidden" name="action" value="reject">
                            <button type="submit" class="btn btn-danger btn-sm">Từ chối</button>
                        </form>
                    </div>
                </details>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
