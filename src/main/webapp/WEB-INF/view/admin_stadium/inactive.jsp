<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/stadium" class="list-group-item list-group-item-action">
                            경기장
                        </a>
                        <a href="/admin/stadium/wait" class="list-group-item list-group-item-action">승인대기</a>
                        <a href="/admin/stadium/inactive" class="list-group-item list-group-item-action active" aria-current="true">비활성화</a>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>

                         <div class="d-flex justify-content-center" id="approveBoard">
                <div style="position: relative; top: 50px">

                <div class="d-flex justify-content-center mb-4">
                    <h3 class="text-secondary">비활성 경기장</h3>
                </div>
                    <table class="table" style="margin-bottom:30px;" >
                     <thead>
                        <tr class="my-text-align">
                            <th scope="col" class="text-center">번호</th>
                            <th scope="col" class="text-center">경기장 이름</th>
                            <th scope="col" class="text-center">사진</th>
                            <th scope="col" class="text-center">등록일</th>
                            <th scope="col" class="text-center">상태</th>
                        </tr>
                     </thead>
                     <tbody id="approveBoardCheck">

                            <c:forEach items="${stadiumInactiveList}" var="stadium">
                                <tr class="my-text-align">
                                    <td class="text-center">${stadium.id}</td>
                                    <td>${stadium.name}</td>
                                    <td><a href="${stadium.fileUrl}"  class="link-with-ellipsis">${stadium.fileUrl}</a></td>
                                    <td class="text-center">${MyDateUtils.toStringFormat(stadium.createdAt)}</td>
                                    <%-- <td>${stadium.status}</td> --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${stadium.status == '비활성'}">
                                            <select id="status-select-${stadium.id}" onchange="confirmActive(${stadium.id})">
                                                <option value="비활성" selected>비활성</option>
                                                <option value="활성">활성</option>
                                            </select>
                                            </c:when>
                                            <c:otherwise>
                                            ${stadium.status}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                </tr>
                            </c:forEach>
                     </tbody>
                    </table>
                    <div id="noResultComment">

                    </div>

                    <div class="d-flex justify-content-center mb-3">
                        <ul class="pagination">

                            <li class='page-item ${nowPage == 1 ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callPrev();">Prev</a></li>

                            <c:forEach var="num" begin="${startPage}" end="${endPage}">

                            <li class="page-item ${num == nowPage ? 'active' : ''}">
                                <a class="page-link" href="/admin/stadium/inactive?page=${num-1}&keyword=${keyword}">${num}</a>
                            </li>
                            </c:forEach>

                            <li class='page-item ${nowPage == totalPage ? "disabled" : ""}'><a class="page-link"
                                    href="javascript:void(0);" onclick="callNext();">Next</a></li>

                        </ul>
                    </div>
                    <div class="input-group justify-content-center" style="position: absolute; bottom: 30;" id="searchBar">
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
            function confirmActive(stadiumId) {
                if (confirm('정말 활성화 하시겠습니까?')) {
                    changeToActive(stadiumId);
                } else {
                    $('#status-select-' + stadiumId).val('비활성');
                }
            }

            function searchGet() {
                let keyword =  $("#keyword").val();
                location.href = "/admin/stadium/inactive?page=0&keyword=" + keyword;
            }
            function callPrev() {
                let requestPage = `${nowPage-2}`;
                let keyword = `${keyword}`
                location.href = "/admin/stadium/inactive?page=" + requestPage+"&keyword="+keyword;
            }

            function callNext() {
                let requestPage = `${nowPage}`;
                let keyword = `${keyword}`
                location.href = "/admin/stadium/inactive?page=" + requestPage+"&keyword="+keyword;
            }
            function changeToActive(stadiumId) {
                $.ajax({
                    url: '/admin/stadium/active/'+stadiumId,
                    method: 'PUT',

                    success: function(response) {
                        console.log(response);
                        // alert('경기장 활성화');
                        location.reload();
                    },
                    error: function(error) {
                    alert('활성화 중 오류가 발생했습니다.');
                    console.log(error);
                    }
                });
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>