document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('search');
    const suggestionsList = document.getElementById('suggestions');



    searchInput.addEventListener('input', function () {
        const query = searchInput.value.trim();
        let firstValue, secondValue;

        // Sprawdź czy zapytanie zawiera spację
        if (query.includes(' ')) {
            // Jeśli zawiera spację, podziel zapytanie na dwie wartości
            [firstValue, secondValue] = query.split(' ');
        } else {
            // Jeśli nie zawiera spację, pierwsza wartość to całe zapytanie, a druga wartość zostanie pusta
            firstValue = query;
            secondValue = '';
        }
        fetch(`/api/search/allRestaurant?first=${encodeURIComponent(firstValue)}&second=${encodeURIComponent(secondValue)}`)
            .then(response => response.json())
            .then(data => {
                suggestionsList.innerHTML = '';
                data.forEach(restaurant => {
                    const option = document.createElement('option');
                    option.value = `${restaurant.name} ${restaurant.city} ${restaurant.category} ` ;
                    option.setAttribute('data-restaurant-id', restaurant.id);
                    suggestionsList.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Wystąpił błąd podczas pobierania sugestii:', error);
            });
    });

});


function redirectToSelectedOption() {
    const selectedOption = document.getElementById('search').value;
    const options = document.getElementById('suggestions').getElementsByTagName('option');
    for (let i = 0; i < options.length; i++) {
        if (options[i].value === selectedOption) {
            let id = options[i].getAttribute('data-restaurant-id');
            window.location.href = `/restaurant/${id}`;
            break;
        }
    }
}

