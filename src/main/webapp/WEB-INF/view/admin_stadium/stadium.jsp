<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/stadium" class="list-group-item list-group-item-action active"  aria-current="true">
                            경기장
                        </a>
                        <a href="/admin/stadium/wait" class="list-group-item list-group-item-action">승인대기</a>
                        <a href="/admin/stadium/inactive" class="list-group-item list-group-item-action">비활성화</a>
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
                            <th scope="col" class="text-center">경기장 이름</th>
                            <th scope="col" class="text-center">연락처</th>
                            <th scope="col" class="text-center">사진</th>
                            <th scope="col" class="text-center">등록 일자</th>
                        </tr>

                        <c:forEach items="${stadiumList}" var="stadium">
                            <tr class="my-text-align">
                                <td class="text-center">${stadium.id}</td>
                                <td>${stadium.name}</td>
                                <td>${stadium.tel}</td>
                                <td><a href="${stadium.fileUrl}"  class="link-with-ellipsis">${stadium.fileUrl}</a></td>
                                <td class="text-center">${MyDateUtils.toStringFormat(stadium.createdAt)}</td>
                                <td><button onclick="courtDelete(${courtList.id})" class="btn-xs">삭제</button></td>
                            </tr>
                        </c:forEach>
                    </table>


                    <div class="d-flex justify-content-center mb-3">
                        <ul class="pagination">

                            <li class='page-item ${nowPage == 1 ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callPrev();">Prev</a></li>

                            <c:forEach var="num" begin="${startPage}" end="${endPage}">

                            <li class="page-item ${num == nowPage ? 'active' : ''}">
                                <a class="page-link" href="/admin/stadium?page=${num-1}&keyword=${keyword}">${num}</a>
                            </li>
                            </c:forEach>

                            <li class='page-item ${nowPage == totalPage ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callNext();">Next</a></li>

                        </ul>
                    </div>
                    <div class="input-group justify-content-center" style="position: absolute; bottom: 30;">
                        <div class="col-4">
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

        <script>
            function searchGet() {
                let keyword =  $("#keyword").val();
                location.href = "/admin/stadium?page=0&keyword=" + keyword;
            }
            function callPrev() {
                let requestPage = `${nowPage-2}`;
                let keyword = `${keyword}`
                location.href = "/admin/stadium?page=" + requestPage+"&keyword="+keyword;
            }

            function callNext() {
                let requestPage = `${nowPage}`;
                let keyword = `${keyword}`
                location.href = "/admin/stadium?page=" + requestPage+"&keyword="+keyword;
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>