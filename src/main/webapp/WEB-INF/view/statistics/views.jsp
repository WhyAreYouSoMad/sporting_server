<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/statistics/connection" class="list-group-item list-group-item-action"  >
                            접속률
                        </a>
                        <a href="/admin/statistics/views" class="list-group-item list-group-item-action   active" aria-current="true">조회수</a>
                        <%-- <a href="/admin/statistics/reservation" class="list-group-item list-group-item-action">예약률</a> --%>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>

            <div class="d-flex justify-content-center">
                <div style="position: relative; top: 50px">

                    <div id="monthForm" class="d-flex justify-content-center" style="font-size: 30px">
                        
                    </div>
                        <div class="d-flex justify-content-end me-4 mx-auto">
                            <ul id="pageForm" class="pagination">
                                <li id="prevButton" class='page-item'><a class="page-link" href="javascript:void(0);" onclick="callPrev();">Prev</a></li>
                                <li id="nextButton" class='page-item'><a class="page-link" href="javascript:void(0);" onclick="callNext();">Next</a></li>
                            </ul>
                        </div>

                        <div id="chartForm" style="width: 900px; height: 900px;">
                            <!--차트가 그려질 부분-->
                            <canvas id="myChart"></canvas>
                        </div>
                   
image.png
            </div>
            
        </div>
         <script type="text/javascript">
            let check = '${paging.check}';
           getViewsData(check)
           
           function getViewsData(check) {
               $('#myChart').remove(); // 이전 데이터를 삭제합니다.
                $('#chartForm').append('<canvas id="myChart"></canvas>'); 
               $.ajax({
                    url: '/admin/statistics/views/data?check='+check,
                    method: 'GET',

                    success: function(response) {
                    var labels = response.data.topStadiumNames;
                    var datas = response.data.viewCounts;
                    var requestMonth = response.data.requestMonth;
                    var first = response.data.first;
                    var last = response.data.last;
                    var check = response.data.check;

                    var el = requestMonth+"월 경기장 조회순";
                    $("#monthForm").empty();
                    $("#monthForm").append(el);

                    const prevButton = document.getElementById("prevButton");
                    const nextButton = document.getElementById("nextButton");

                    if (first) {
                        prevButton.classList.add("disabled");
                    } else {
                        prevButton.classList.remove("disabled");
                    }
                    if (last) {
                        nextButton.classList.add("disabled");
                    } else {
                        nextButton.classList.remove("disabled");
                    }

                        var context = document.getElementById('myChart').getContext('2d');
                        var myChart = new Chart(context, {
                            type: 'bar', // 차트의 형태
                            data: { // 차트에 들어갈 데이터
                                labels: labels,

                                datasets: [
                                    { //데이터
                                        label: '조회 수', //차트 제목
                                        fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
                                        data: datas,

                                        backgroundColor: [
                                            //색상
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                            'rgba(54, 162, 235, 0.2)',
                                        ],
                                        borderColor: [
                                            //경계선 색상
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            'rgba(0, 0, 0, 0.5)',
                                            
                                        ],
                                        borderWidth: 1 //경계선 굵기
                                    }
                                ]
                            },
                            options: {
                                 title: {
                                     display: false // 제목 표시 안 함
                                 },
                                 legend: {
                                     display: false // 범례 표시 안 함
                                 },
                                scales: {
                                    yAxes: [
                                        {
                                            ticks: {
                                                beginAtZero: true
                                            }
                                        }
                                    ]
                                }
                            }
                        });

                                    },
                    error: function(error) {
                    // 에러 처리
                    alert('삭제 중 오류가 발생했습니다.');
                    console.log(error);
                    }
                });
            }
         

            function callPrev() {
                
            //    const chartContainer = document.querySelector('#chartForm');
            //     chartContainer.removeChild(chartContainer.firstChild);
                
                check++;
                getViewsData(check);
            }

            function callNext() {

                // const chartContainer = document.querySelector('#chartForm');
                // chartContainer.removeChild(chartContainer.firstChild);
                
                check--;
                getViewsData(check);
            }

        </script>

        <%@ include file="../layout/footer.jsp" %>