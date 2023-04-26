<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/court" class="list-group-item list-group-item-action">
                            코트
                        </a>
                        <a href="/admin/court/wait" class="list-group-item list-group-item-action active" aria-current="true">승인대기</a>
                        <a href="/admin/court/inactive" class="list-group-item list-group-item-action" >비활성화</a>
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
                            <th scope="col" class="text-center">코트이름</th>
                            <th scope="col" class="text-center">코트가격</th>
                            <th scope="col" class="text-center">수용 인원</th>
                            <th scope="col" class="text-center">등록 일자</th>
                            <th scope="col" class="text-center"></th>
                        </tr>

                        <c:forEach items="${courtList}" var="courtList">
                            <tr class="my-text-align">
                                <td class="text-center">${courtList.id}</td>
                                <td>${courtList.title}</td>
                                <td>${courtList.courtPrice}</td>
                                <td>${courtList.capacity}</td>
                                <td class="text-center">${MyDateUtils.toStringFormat(courtList.createdAt)}</td>
                                 <td><button onclick="changeStatus(${courtList.id})" class="btn-xs">승인</button></td>
                            </tr>
                        </c:forEach>
                    </table>


                    <div class="d-flex justify-content-center mb-3">
                        <ul class="pagination">

                            <li class='page-item ${nowPage == 1 ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callPrev();">Prev</a></li>

                            <c:forEach var="num" begin="${startPage}" end="${endPage}">

                            <li class="page-item ${num == nowPage ? 'active' : ''}">
                                <a class="page-link" href="/admin/court/wait?page=${num-1}&keyword=${keyword}">${num}</a>
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

        <script>
            function searchGet() {
                let keyword =  $("#keyword").val();
                location.href = "/admin/court/wait?page=0&keyword=" + keyword;
            }
            function callPrev() {
                let requestPage = `${nowPage-2}`;
                let keyword = `${keyword}`
                location.href = "/admin/court/wait?page=" + requestPage+"&keyword="+keyword;
            }

            function callNext() {
                let requestPage = `${nowPage}`;
                let keyword = `${keyword}`
                location.href = "/admin/court/waitt?page=" + requestPage+"&keyword="+keyword;
            }


// 이거부터 하면 됨
            function changeStatus(courtId) {
                $.ajax({
                    url: '/admin/court/status',
                    method: 'POST',
                    data: { courtId: courtId },
                    success: function(response) {
                        alert('승인 완료!');
                        location.reload();
                    },
                    error: function(error) {
                    // 에러 처리
                    alert('승인 중 오류가 발생했습니다.');
                    console.log(error);
                    }
                });
            }
        </script>

        <%@ include file="../layout/footer.jsp" %>