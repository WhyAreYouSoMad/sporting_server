<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/court" class="list-group-item list-group-item-action">
                            코트
                        </a>
                        <a href="/admin/court/wait" class="list-group-item list-group-item-action">승인대기</a>
                        <a href="/admin/court/inactive" class="list-group-item list-group-item-action active" aria-current="true">비활성화</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>


        </div>
        <%@ include file="../layout/footer.jsp" %>