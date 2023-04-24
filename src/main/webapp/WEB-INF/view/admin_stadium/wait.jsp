<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/stadium" class="list-group-item list-group-item-action">
                            경기장
                        </a>
                        <a href="/admin/stadium/wait" class="list-group-item list-group-item-action active" aria-current="true">승인대기</a>
                        <a href="/admin/stadium/inactive" class="list-group-item list-group-item-action" >비활성화</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>


        </div>
        <%@ include file="../layout/footer.jsp" %>