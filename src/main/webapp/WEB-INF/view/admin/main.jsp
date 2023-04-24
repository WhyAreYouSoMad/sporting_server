<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>


        <div class="container" style="height: 480px">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card" style="height: 400px; width: 350px; margin: auto; margin-top: 40px;">
                        <div class="card-header" style="background-color: #bfc2c0;">
                            <h3 class="text-center">관리</h3>
                        </div>
                        <div class="card-body">
                            <div class="list-group">
                                <a href="/admin/user/player" class="list-group-item list-group-item-action">유저관리</a>
                                <a href="/admin/stadium" class="list-group-item list-group-item-action">경기장관리</a>
                                <a href="/admin/court" class="list-group-item list-group-item-action">코트관리</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>




        <%@ include file="../layout/footer.jsp" %>