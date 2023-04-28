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
                <div class="d-flex justify-content-end mb-3">
                <button class="btn btn-warning mx-1" onclick="addCheckBox()">Check</button>
                <button class="btn btn-primary mx-1" onclick="openEmailForm()">Send</button>
                </div> 
                    <table class="table">

                        <tr class="my-text-align">
                            <th scope="col" style="width: 30px">#</th>

                            <th scope="col" class="text-center">번호</th>
                            <th scope="col" class="text-center">이메일</th>
                            <th scope="col" class="text-center">닉네임</th>
                            <th scope="col" class="text-center">가입일자</th>
                            <th scope="col" class="text-center"></th>
                        </tr>

                        <c:forEach items="${userList}" var="user">
                            <tr class="my-text-align">
                                <td>
                                        
                                </td>
                                <td class="text-center">${user.id}</td>
                                <td>${user.email}</td>
                                <td>${user.nickname}</td>
                                <td class="text-center">${MyDateUtils.toStringFormat(user.createdAt)}</td>
                                <td><button onclick="userDelete(${user.id})" class="btn-xs">삭제</button></td>
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
            <div class="modal fade" id="emailModal" tabindex="-1" role="dialog" aria-labelledby="emailModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                <div class="modal-header justify-content-center">
                
                    <h5 class="modal-title" id="emailModalLabel">이메일 전송</h5>
                    <%-- <button type="button" onclick="closeModal()" class="close" data-dismiss="modal" aria-label="Close"> --%>
                    <%-- <span aria-hidden="true">&times;</span> --%>
                    <%-- </button> --%>
                </div>
                <div class="modal-body">
                    <%-- <h4>목록</h4> --%>
                    <ul id="emailList" class="list-unstyled ms-2"></ul>
                    <h3 class="font-weight-bold">제목</h3>
                    <input id="emailSubject" type="text" class="form-control"/>
                    <h3 class="font-weight-bold">내용</h3>
                    <textarea id="emailContent" rows="17" class="form-control"></textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeModal()">Close</button>
                    <button id="sendEmailBtn" type="button" class="btn btn-primary" onclick="sendEmail()">Send</button>
                </div>
                </div>
            </div>
            </div>
        <script>
        let emails;
        function openEmailForm() {
            let checkBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
            if (checkBoxes.length > 0) { // 체크된 체크박스가 있는 경우
                emails = [];
                for (let i = 0; i < checkBoxes.length; i++) {
                emails.push(checkBoxes[i].value); // 체크된 이메일을 배열에 추가
                }

                // 이메일 목록을 모달 내부에 추가
                let emailList = document.getElementById("emailList");
                emailList.innerHTML = ""; // 초기화
                for (let i = 0; i < emails.length; i++) {
                let email = document.createElement("li");
                email.className = "text-primary font-weight-bold";
                email.textContent = emails[i];
                emailList.appendChild(email);
                }

                // 모달을 띄움
                $('#emailModal').modal('show');
            } else { // 체크된 체크박스가 없는 경우
                alert('체크박스를 선택해주세요.');
            }
        }

        function sendEmail() {
            
        let emailList = []; // 이메일과 id를 저장할 배열

        for (let i = 0; i < emails.length; i++) {
            let email = emails[i];
            let checkboxes = document.getElementsByName('userIds[]');
            for (let j = 0; j < checkboxes.length; j++) {
                if (checkboxes[j].checked && checkboxes[j].value === email) { // 체크된 체크박스 중 이메일과 일치하는 것을 찾음
                    let row = checkboxes[j].parentNode.parentNode;
                    let id = row.querySelector('td:nth-child(2)').textContent; // row의 id 값
                    let emailObj = { id: id, email: email }; // 이메일과 id를 객체에 추가
                    emailList.push(emailObj); // 객체를 배열에 추가
                }
            }
        }
        // console.log(emailList);
        let emailSubject = $("#emailSubject").val();
        let emailContent = $("#emailContent").val();
        
        let emailDTO = {
            emailList: emailList,
            title: emailSubject,
            content: emailContent
        }
            $.ajax({
                type: 'POST',
                url: '/admin/email',
                data: JSON.stringify(emailDTO),
                contentType: 'application/json',
                success: function(response) {
                    // Handle success response from the server
                    console.log('Email sent successfully!');
                    console.log(response);
                },
                error: function(error) {
                    // Handle error response from the server
                    console.log('Error sending email:', error);
                }
            });

            $('#emailSubject').val(''); // 제목 입력 폼 초기화
            $('#emailContent').val(''); // 내용 입력 폼 초기화
            $('#emailList').empty(); // 이메일 목록 초기화
            $('#emailModal').modal('hide'); // 모달창 닫기
         }

        function closeModal() {
            $('#emailSubject').val(''); 
            $('#emailContent').val(''); 
            $('#emailList').empty(); 
            $('#emailModal').modal('hide'); 
        }
        let checkBoxAdded = false; // 체크박스 추가 여부를 저장하는 변수

        function addCheckBox() {
            if (!checkBoxAdded) { // 체크박스가 추가되어 있지 않은 경우
                let checkBoxes = document.getElementsByTagName('td'); // 모든 td 태그를 가져옴
                for (let i = 0; i < checkBoxes.length; i++) {
                    let td = checkBoxes[i];
                    if (td.innerHTML.trim() === '') { // td 태그의 내용이 비어 있는 경우
                        td.innerHTML = '<input type="checkbox" name="userIds[]" value="' + td.parentElement.cells[2].innerText + '"/>'; // 체크박스를 추가
                    }
                }
                checkBoxAdded = true; // 체크박스가 추가되었다고 표시
            } else { // 체크박스가 추가되어 있는 경우
                let checkBoxes = document.querySelectorAll('input[type="checkbox"]');
                for (let i = 0; i < checkBoxes.length; i++) {
                    let checkBox = checkBoxes[i];
                    checkBox.parentElement.innerHTML = ''; // 체크박스를 삭제
                }
                checkBoxAdded = false; // 체크박스가 삭제되었다고 표시
            }
        }


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

        function userDelete(userId) {
            $.ajax({
                url: '/admin/user/delete',
                method: 'POST',
                data: { userId: userId },
                success: function(response) {
                    alert('악질유저 삭제 완료!');
                    location.reload();
                },
                error: function(error) {
                // 에러 처리
                alert('삭제 중 오류가 발생했습니다.');
                console.log(error);
                }
            });
        }

        </script>

        <%@ include file="../layout/footer.jsp" %>