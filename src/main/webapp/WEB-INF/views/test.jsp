<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hwany
  Date: 2020-05-15
  Time: 오전 9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <title>내 주변 진료소 조회</title>
</head>
<body>
<div id="map" style="width:100%;height:700px;"></div>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=p64anon2ax"></script>
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script>
    var latitude = "";
    var longitude = "";
    var clinic_list = null;
    var map = null;
    var markers = [],
        infoWindows = [];

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            function (pos) {
                latitude = pos.coords.latitude;
                longitude = pos.coords.longitude;
                // $("#board").append(latitude + ", " + longitude);
                console.log(latitude + ", " + longitude);
                //getWeather();
                initMap(latitude,longitude);
                getClinicByDistance(latitude,longitude);
            },
            function (error) {
                console.log(error.message);
                latitude = "37.5623396";
                longitude = "126.9728114";
            }, {
                enableHighAccuracy: true
                , timeout: 1000
            }
        );
    }
    function initMap(lat, lon){
        var position = new naver.maps.LatLng(lat, lon);
        var mapOptions = {
            center: position,
            zoom: 15
        };

        map = new naver.maps.Map('map', mapOptions);

        var markerOptions = {
            position: position,
            map: map,
            icon: {
                url: './img/pin_default.png',
                size: new naver.maps.Size(22, 35),
                origin: new naver.maps.Point(0, 0),
                anchor: new naver.maps.Point(11, 35)
            }
        };
        var marker = new naver.maps.Marker(markerOptions);
        setCircle();
    }
    // naver.maps.Event.addListener(map, 'idle', function() {
    //     updateMarkers(map, markers);
    // });

    function updateMarkers(map, markers) {

        var mapBounds = map.getBounds();
        var marker, position;

        for (var i = 0; i < markers.length; i++) {

            marker = markers[i];
            position = marker.getPosition();

            if (mapBounds.hasLatLng(position)) {
                showMarker(map, marker);
            } else {
                hideMarker(map, marker);
            }
        }
    }

    function showMarker(map, marker) {

        if (marker.setMap()) return;
        marker.setMap(map);
    }

    function hideMarker(map, marker) {

        if (!marker.setMap()) return;
        marker.setMap(null);
    }

    // 해당 마커의 인덱스를 seq라는 클로저 변수로 저장하는 이벤트 핸들러를 반환합니다.
    function getClickHandler(seq) {
        return function(e) {
            var marker = markers[seq],
                infoWindow = infoWindows[seq];

            if (infoWindow.getMap()) {
                infoWindow.close();
            } else {
                infoWindow.open(map, marker);
            }
        }
    }

    function setCircle(){
        var circle = new naver.maps.Circle({
            map: map,
            center: map.center,
            radius: 1000,
            fillColor: 'crimson',
            fillOpacity: 0.2
        });
    }
    function getClinicByDistance(lat,lon){
        var url = "52.79.243.246:8080/bundaegi/api/clinic/location/";
        var distance = 1;
        var data = {
                lat: lat,
                lon: lon
        };
        console.log(url + distance);
        console.log(data);
        $.ajax({
            url: url + distance,
            type: "GET",
            data: data,
            dataType: "json",
            contentType : "application/json; charset=utf-8",
            success: function (rtnValue) {
                console.log(rtnValue);
                clinic_list = rtnValue.data;
                setClinicByDistance();
            }, error: function (a, b, c) {
                alert("서버와의 통신 중 오류가 발생하였습니다.");
            }
        });
    }
    function setClinicByDistance(){
        for(var idx in clinic_list){
            var lat = clinic_list[idx].clinicLat;
            var lon = clinic_list[idx].clinicLon;
            var clinic = clinic_list[idx].clinicName;
            console.log(clinic);

            var position = new naver.maps.LatLng(lat, lon);

            var marker = new naver.maps.Marker({
                position: position,
                map: map,
                icon: {
                    url: './img/pin_default.png',
                    size: new naver.maps.Size(22, 35),
                    origin: new naver.maps.Point(0, 0),
                    anchor: new naver.maps.Point(11, 35)
                }
            });

            var infoWindow = new naver.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:10px;"><b>'+ clinic +'</b></div>'
            });

            markers.push(marker);
            infoWindows.push(infoWindow);

            // for (var i=0; i < markers.length; i++) {
            //     naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
            // }
        }
    }

</script>
</body>
</html>