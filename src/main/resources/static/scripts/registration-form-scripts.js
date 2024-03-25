const closeButton = document.querySelector( '.register-close-notification-button' );
const notificationContainer = document.querySelector( '.register-notyfication-container' );

closeButton.addEventListener( 'click', function () {
    notificationContainer.style.display = 'none';
});