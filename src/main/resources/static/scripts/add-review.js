const form = document.getElementById("addReviewForm");
const responseContainer = document.getElementById("review-list");
const addReviewContainer = document.getElementById("add-review-container");

const currentUrl = window.location.href;
const urlParts = currentUrl.split('/');
const restaurantId = urlParts[urlParts.length - 1];
document.addEventListener('DOMContentLoaded', function() {

    fetch(`/api/review/checkReviewStatus/` + restaurantId )
        .then(response => response.json())
        .then(hasReviewed => {
            const toggleFormButton = document.getElementById('toggleFormButton');
            const spanText = document.querySelector('#toggleFormButton .home-text12');

            if (hasReviewed) {
                form.style.display = 'none';
                spanText.textContent = 'Edytuj Recenzję';
            } else {
                form.style.display = 'none';
                spanText.textContent = 'Dodaj Recenzję';
            }
            toggleFormButton.addEventListener('click', function (){
                toggleFormVisibility();
                spanText.textContent = (form.style.display === 'none') ? 'Dodaj Recenzję' : 'Anuluj';
            });

        })
        .catch(error => console.error('Błąd podczas pobierania informacji o recenzji:', error));
    function toggleFormVisibility() {
        if (form.style.display === 'none') {
            form.style.display = 'block';
        } else {
            form.style.display = 'none';
        }
    }
});
form.onsubmit = function (e) {
    e.preventDefault();

    let formData = new FormData(form);
    let jsonData = {};
    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

    let csrfToken = formData.get('_csrf');

    let headers = {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': csrfToken
    };

    fetch(form.action, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(jsonData)
    }).then(response => response.json())
        .then(data => {

            let newListItem = document.createElement("li");
            newListItem.innerHTML = `
                                    <li>
                                        <div class="review">
                                            <div class="review-date">
                                                <h4>${data.date}</h4>
                                             </div>
                                           <h3 class="review-author">${data.login}</h3>
                                                <span>Ocena smaku: ${data.ratingTaste}/5</span>
                                                <span>Ocena atmosfery: ${data.ratingAtmosphere}/5</span>
                                                <span>Ocena obsługi: ${data.ratingService}/5</span>
                                                <p class="review-text">${data.reviewContent}</p>
                                        </div>
                                    </li>`;
            responseContainer.insertAdjacentHTML('afterbegin', `<li>${newListItem.outerHTML}</li>`);
            form.reset();
            addReviewContainer.style.display = "none";
            updateRating();
        })
        .catch(error => {
            console.error('Błąd podczas przetwarzania żądania:', error);
        });
}
async function updateRating() {
    try {
        const response = await fetch('/api/review/rating/' + restaurantId);
        if (!response.ok) {
            console.error('Wystąpił błąd podczas pobierania ocen.' + restaurantId);
        }
        const ratingDto = await response.json();
        if (ratingDto && ratingDto.avgAllRatings !== null && ratingDto.avgAllRatings !== undefined) {
            document.getElementById('avgAllRatings').innerText = ratingDto.avgAllRatings.toFixed(1).replace('.', ',') + '/5,0';
            document.getElementById('countRatings').innerText = 'Liczba ocen: ' + ratingDto.countRating.toFixed();
            document.getElementById('avgRatingTaste').innerText = ratingDto.avgRatingTaste.toFixed(1).replace('.', ',') + '/5,0';
            document.getElementById('avgRatingAtmosphere').innerText = ratingDto.avgRatingAtmosphere.toFixed(1).replace('.', ',') + '/5,0';
            document.getElementById('avgRatingService').innerText = ratingDto.avgRatingService.toFixed(1).replace('.', ',') + '/5,0';
        }
    } catch (error) {
        console.error(error.message);
    }
}