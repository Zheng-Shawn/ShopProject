

//從網址上獲取值
let urlParams = new URLSearchParams(window.location.search);
let productId = parseInt(urlParams.get('productId'));

$(document).ready(function () {

    //商品資訊請求
    $.ajax({
        url: `/Shawn/select_product/${productId}`,
        method: 'GET',
        contentType: 'application/json',
        success: (response) => {
            success(response);
        },
        error: (error) => {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.status
            })
        }
    })
    function success(response) {

        //處理時間格式
        const date = new Date(response.created_date);
        const formatdate = () => {
            return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
        }

        $(".producttitle").text(response.product_name);
        $("#category").text(response.category);
        $("#brand").text(response.brand);
        $(".price").text(response.price);
        $("#sold").text(response.sold + '件');
        $("#home-tab-pane").text(response.description);
        $(".createdate").text(formatdate());

    }
    // 圖片請求
    $.ajax({
        url: `/Shawn/product/pictures/${productId}`,
        method: 'GET',
        contentType: 'application/json',
        success: (response) => {
            success2(response);
        },
        error: (error) => {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.status
            })
        }
    })
    function success2(response) {

        for (let i = 0; i < response.length; i++) {
            let pic = response[i].productPic;
            let picurl = 'data:image/png;base64,' + pic;

            $(".carousel-inner").append(`
                <div class="carousel-item">
                    <img src="${picurl}" class="d-block w-100" alt="...">
                </div>
            `)
        }

        $(".carousel-item").first().addClass("active");

    }

})

function addcart() {


    $.ajax({
        url: `/Shawn/addcart/${productId}`,
        method: 'GET',
        success: (response) => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: '加入成功!',
                showConfirmButton: false,
                timer: 1500
            })

            
        },
        error: (error) => {

            // 使用cookie 儲存購物車資訊
            // 點選購物車按紐 判斷有無會員
            // 註冊完成後 判斷有無購物車cookie並把cookie轉換成session儲存


            Swal.fire({
                icon: 'error',
                title: '您不是會員',
                text: error.status
            })

        }
    })


}


