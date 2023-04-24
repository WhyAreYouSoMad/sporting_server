<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/user/player" class="list-group-item list-group-item-action" >
                            일반회원
                        </a>
                        <a href="/admin/user/company" class="list-group-item list-group-item-action" >기업회원</a>
                        <a href="/admin/user/wait" class="list-group-item list-group-item-action  active" aria-current="true">승인대기</a>
                        <a href="/admin/user/inactive" class="list-group-item list-group-item-action" >비활성회원</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>


        </div>
        <%@ include file="../layout/footer.jsp" %>