<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>



        <div style="position :relative; height: 700px;">

            <div class="d-flex" style="position: absolute; left: 2px">

                <div class="container my-3">
                    <div class="list-group">
                        <a href="/admin/statistics/connection" class="list-group-item list-group-item-action  active"  aria-current="true">
                            접속률
                        </a>
                        <a href="/admin/statistics/views" class="list-group-item list-group-item-action">조회수</a>
                        <%-- <a href="" class="list-group-item list-group-item-action">예약률</a> --%>
                    </div>
                </div>

                <div class="vl">

                </div>

            </div>

            <div class="d-flex justify-content-center">
                <div style="position: relative; top: 50px">
                    <canvas id="myChart" width="800" height="500"></canvas>

                </div>
            </div>
            
        </div>

    </body>
</html>

    <script type="text/javascript">
            getConnectionData()
           function getConnectionData() {
                $.ajax({
                    url: '/admin/statistics/connection/data',
                    method: 'GET',

                    success: function(response) {
                    console.log();

                    var labels = response.data.recentHalfYear;
                    var data1 = response.data.playerConnectionData;
                    var data2 = response.data.compnayConnectionData;

                    var context = document
                        .getElementById('myChart')
                        .getContext('2d');


                    var myChart = new Chart(context, {
                        type: 'line', // 차트의 형태
                        data: { // 차트에 들어갈 데이터
                            labels: labels, // x 축

                            datasets: [
                                {
                                    label: 'Player',
                                    fill: false,
                                    data: data1,
                                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                    borderColor: 'rgba(255, 99, 132, 1)',
                                    borderWidth: 1
                                },
                                {
                                    label: 'Company',
                                    fill: false,
                                    data: data2,
                                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                }
                            ]
                        },
                        options: {
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
        </script>

        <%@ include file="../layout/footer.jsp" %>