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
        fetch(`/api/search/all?first=${encodeURIComponent(firstValue)}&second=${encodeURIComponent(secondValue)}`)
            .then(response => response.json())
            .then(data => {
                suggestionsList.innerHTML = '';
                data.forEach(restaurant => {
                    const option = document.createElement('option');
                    option.value = `${restaurant.name} ${restaurant.city} ${restaurant.category}`;
                    option.setAttribute('data-restaurant-id', restaurant.id);
                    suggestionsList.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Wystąpił błąd podczas pobierania sugestii:', error);
            });
    });
});

