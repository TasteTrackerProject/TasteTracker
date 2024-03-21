const form = document.getElementById("addReviewForm");
const responseContainer = document.getElementById("review-list");
const addReviewContainer = document.getElementById("add-review-container");

const currentUrl = window.location.href;
const urlParts = currentUrl.split('/');
const restaurantId = urlParts[urlParts.length - 1];
document.addEventListener('DOMContentLoaded', function () {
    const toggleFormButton = document.getElementById('toggleFormButton');
    const spanText = document.querySelector('#toggleFormButton .home-text12');

    toggleFormButton.addEventListener('click', function () {
        const isEditing = (form.style.display === 'block');
        toggleFormVisibility();
        spanText.textContent = isEditing ? 'Edytuj Recenzję' : 'Anuluj';

        if (!isEditing) { // Jeśli użytkownik nacisnął przycisk "Edytuj Recenzję"
            fetch(`/api/review/getReview/${restaurantId}`)
                .then(response => response.json())
                .then(reviewData => {
                    // Wypełnij formularz danymi z istniejącej recenzji
                    document.getElementById('review').value = reviewData.reviewContent;
                    // Wypełnij oceny danymi z istniejącej recenzji
                    document.querySelector(`input[name="ratingTaste"][value="${reviewData.ratingTaste}"]`).checked = true;
                    document.querySelector(`input[name="ratingAtmosphere"][value="${reviewData.ratingAtmosphere}"]`).checked = true;
                    document.querySelector(`input[name="ratingService"][value="${reviewData.ratingService}"]`).checked = true;
                })
                .catch(error => console.error('Błąd podczas pobierania istniejącej recenzji:', error));
        }
    });

    fetch(`/api/review/checkReviewStatus/` + restaurantId)
        .then(response => response.json())
        .then(hasReviewed => {
            if (hasReviewed) {
                form.style.display = 'none';
                spanText.textContent = 'Edytuj Recenzję';
            } else {
                form.style.display = 'none';
                spanText.textContent = 'Dodaj Recenzję';
            }
        })
        .catch(error => console.error('Błąd podczas pobierania informacji o recenzji:', error));

});

function toggleFormVisibility() {
    if (form.style.display === 'none') {
        form.style.display = 'block';
    } else {
        form.style.display = 'none';
    }
}

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

            let existingReview = document.querySelector(`.review .review-author#review-author-done `);
            if (existingReview && existingReview.textContent.trim() === data.login) {

                let reviewContainer = existingReview.closest('.review');
                if (reviewContainer) {
                    // Aktualizacja istniejącej recenzji
                    reviewContainer.querySelector(".review-date h4").textContent = data.date;
                    reviewContainer.querySelector(".review-author").textContent = data.login;
                    reviewContainer.querySelector(".review-text").textContent = data.reviewContent;
                    reviewContainer.querySelector("#rating-taste").textContent = `Ocena smaku: ${data.ratingTaste}/5`;
                    reviewContainer.querySelector("#rating-atmosphere").textContent = `Ocena atmosfery: ${data.ratingAtmosphere}/5`;
                    reviewContainer.querySelector("#rating-service").textContent = `Ocena obsługi: ${data.ratingService}/5`;
                } else {
                    console.error("Nie można znaleźć kontenera recenzji.");
                }
            } else {
                // Dodanie nowej recenzji
                let newListItem = document.createElement("li");
                newListItem.innerHTML = `
                    <div class="review" data-review-id="${data.reviewId}">
                        <div class="review-date">
                            <h4>${data.date}</h4>
                        </div>
                        <h3 class="review-author">${data.login}</h3>
                        <span class="rating-taste">Ocena smaku: ${data.ratingTaste}/5</span>
                        <span class="rating-atmosphere">Ocena atmosfery: ${data.ratingAtmosphere}/5</span>
                        <span class="rating-service">Ocena obsługi: ${data.ratingService}/5</span>
                        <p class="review-text">${data.reviewContent}</p>
                    </div>`;
                responseContainer.insertAdjacentHTML('afterbegin', `<li>${newListItem.outerHTML}</li>`);
            }
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