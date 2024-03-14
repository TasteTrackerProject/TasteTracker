document.addEventListener('DOMContentLoaded', function () {
    const searchInput = document.getElementById('search');
    const suggestionsList = document.getElementById('suggestions');

    searchInput.addEventListener('input', function () {
        const query = searchInput.value.trim();
        fetch(`/api/search?name=${query}`)
            .then(response => response.json())
            .then(data => {
                suggestionsList.innerHTML = ''; // Wyczyść listę przed dodaniem nowych sugestii
                data.forEach(restaurant => {
                    const option = document.createElement('option');
                    option.value = `${restaurant.name}  ${restaurant.city}`;
                    option.setAttribute('data-restaurant-id', restaurant.id);
                    suggestionsList.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Wystąpił błąd podczas pobierania sugestii:', error);
            });
    });
});

