<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>


            <div class="container my-3 d-flex justify-content-center align-items-center" style="height: 150px">
            <form action="/login" method="post">
                <div class="form-group mb-2">
                <input type="text" name="email" class="form-control" placeholder="Enter Admin Email" id="email" size="40">
                </div>
                <div class="form-group mb-2">
                <input type="password" name="password" class="form-control" placeholder="Enter password" id="password" size="40">
                </div>
                <div class="d-flex justify-content-end">
                <button type="submit" class="btn btn-secondary">로그인</button>
                </div>
            </form>
            </div>
        <%@ include file="../layout/footer.jsp" %>