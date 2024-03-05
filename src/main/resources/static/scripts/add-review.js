
    document.getElementById('reviewForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const radioInputs = document.querySelectorAll('input[type="radio"]');

// Inicjuj obiekt do przechowywania wartości dla każdej grupy
        const selectedValues = {};

// Iteruj przez wszystkie inputy
        radioInputs.forEach((radioInput) => {
            // Pobierz nazwę grupy
            const groupName = radioInput.getAttribute('name');

            // Sprawdź, czy radioInput jest zaznaczony
            if (radioInput.checked) {
                // Sprawdź, czy dla danej grupy już istnieje wpis w obiekcie
                selectedValues[groupName] = radioInput.value;
            }
        });

// Teraz 'selectedValues' zawiera wartości dla każdej grupy
        const taste = selectedValues.ratingTaste;
        const atmosphere = selectedValues.ratingAtmosphere;
        const service = selectedValues.ratingService;
        const reviewContent = document.getElementById('reviewContent').value;
        const userName = document.querySelector('p.review-author#userName').textContent;

        const currentUrl = window.location.href;

// Pobierz identyfikator z linku strony (np. /example/{id})
        const match = currentUrl.match(/\/restaurant\/(\d+)/);
        const idFromUrl = match ? match[1] : null;


        const data = {
            content: reviewContent,
            rTaste: taste,
            rAtmosphere: atmosphere,
            rService: service
            // Dodaj inne pola do obiektu 'data'
        };
        console.log(idFromUrl)

        fetch('/api/addReview?' + idFromUrl.toString(), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(review => {
                displayReview(review)
            })
        .catch(error => console.error('Błąd,' , error));
    });

    function displayReview(review) {
        const reviewsContainer = document.getElementById('restaurant-reviews');
        const reviewElement = document.createElement('div');
        reviewElement.innerHTML = `<p>${review.content}</p>`;
        reviewElement.innerHTML = `<p>${review.username}</p>`;
        // Dodaj inne pola do wyświetlania w elemencie 'reviewElement'
        reviewsContainer.appendChild(reviewElement);
    }


//
//
//     fetch('/api/addReview', {
//     method: 'POST',
//     headers: {
//     'Content-Type': 'application/json',
// },
//     body: JSON.stringify(data),
// })
//     .then(response => response.json())
//     .then(review => {
//     displayReview(review);
// })
//     .catch(error => console.error('Błąd:', error));
// });
//
//     function displayReview(review) {
//     const reviewsContainer = document.getElementById('reviewsContainer');
//     const reviewElement = document.createElement('div');
//     reviewElement.innerHTML = `<p>${review.content}</p>`;
//     // Dodaj inne pola do wyświetlania w elemencie 'reviewElement'
//     reviewsContainer.appendChild(reviewElement);
// }

