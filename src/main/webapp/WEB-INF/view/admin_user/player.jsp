<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>
        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/user/player" class="list-group-item list-group-item-action active"
                            aria-current="true">
                            일반회원
                        </a>
                        <a href="/admin/user/company" class="list-group-item list-group-item-action">기업회원</a>
                        <a href="/admin/user/wait" class="list-group-item list-group-item-action">승인대기</a>
                        <a href="/admin/user/inactive" class="list-group-item list-group-item-action">비활성회원</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>


            <div class="d-flex justify-content-center">
                <div style="position: relative; top: 50px">
                    <table class="table">

                        <tr class="my-text-align">
                            <th scope="col" class="text-center">번호</th>
                            <th scope="col" class="text-center">이메일</th>
                            <th scope="col" class="text-center">닉네임</th>
                            <th scope="col" class="text-center">가입일자</th>
                            <th scope="col" class="text-center"></th>
                        </tr>

                        <c:forEach items="${userList}" var="user">
                            <tr class="my-text-align">
                                <td class="text-center">${user.id}</td>
                                <td>${user.email}</td>
                                <td>${user.nickname}</td>
                                <td class="text-center">${user.createdAt}</td>
                                <td><button onclick="" class="btn-xs">삭제</button></td>
                            </tr>
                        </c:forEach>







                    </table>
                    <div class="d-flex justify-content-center mb-3">
                        <ul class="pagination">

                            <li class='page-item ${nowPage == 1 ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callPrev();">Prev</a></li>

                            <c:forEach var="num" begin="${startPage}" end="${endPage}">

                            <li class="page-item ${num == nowPage ? 'active' : ''}">
                                <a class="page-link" href="/admin/user/player?page=${num-1}&keyword=${keyword}">${num}</a>
                            </li>
                            </c:forEach>

                            <li class='page-item ${nowPage == totalPage ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callNext();">Next</a></li>

                        </ul>
                    </div>
                    <div class="input-group justify-content-center" style="position: absolute; bottom: 30;">
                        <div class="col-6">
                            <input id="keyword" name="query" type="text" class="form-control" placeholder="검색어 입력"
                                aria-label="search" value="" aria-describedby="button-addon2">
                        </div>
                        <div class="col-auto">
                            <button id="button-addon2" class="btn btn-primary" type="submit"
                                onclick="searchGet()">검색</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="mx-quto input-group justify-content-center" style="position:absolute; bottom: 300px">
            <div>



            </div>
            <div>
            </div>
        </div>

        </div>

        <script>
            function searchGet() {
                let keyword =  $("#keyword").val();
                location.href = "/admin/user/player?page=0&keyword=" + keyword;
            }
            function callPrev() {
                let requestPage = `${nowPage-2}`;
                let keyword = `${keyword}`
                location.href = "/admin/user/player?page=" + requestPage+"&keyword="+keyword;
            }

            function callNext() {
                let requestPage = `${nowPage}`;
                let keyword = `${keyword}`
                location.href = "/admin/user/player?page=" + requestPage+"&keyword="+keyword;
            }

        </script>

        <%@ include file="../layout/footer.jsp" %>