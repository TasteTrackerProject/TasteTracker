document.addEventListener( 'DOMContentLoaded', function()
{
    const form = document.getElementById( 'registration-form' );
    const passwordInput = document.getElementById( 'password' );
    const confirmPasswordInput = document.getElementById( 'confirm-password' );
    const togglePasswordButton = document.getElementById( 'toggle-password' );

    togglePasswordButton.addEventListener( 'click', function( event )
    {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute( 'type', type );
        confirmPasswordInput.setAttribute( 'type', type );
        togglePasswordButton.textContent = type === 'password' ? 'Pokaż hasło' : 'Ukryj hasło';
    });

    form.addEventListener( 'submit', function( event )
    {
        const loginInput = document.getElementById( 'login' );
        const emailInput = document.getElementById( 'user_email' );
        const passwordInput = document.getElementById( 'password' );
        const confirmPasswordInput = document.getElementById( 'confirm-password' );

        const loginValue = loginInput.value.trim();
        const emailValue = emailInput.value.trim();
        const passwordValue = passwordInput.value.trim();
        const confirmPasswordValue = confirmPasswordInput.value.trim();

        const loginError = document.getElementById( 'login-error' );
        const emailError = document.getElementById( 'email-error' );
        const passwordError = document.getElementById( 'password-error' );
        const confirmPasswordError = document.getElementById( 'confirm-password-error' );

        let isValid = true;

        if ( loginValue.length < 3 || loginValue.length > 12 )
        {
            loginError.textContent = 'Login musi mieć długość od 3 do 12 znaków.';
            isValid = false;
        }
        else
        {
            loginError.textContent = '';
        }

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if ( !emailPattern.test( emailValue ) )
        {
            emailError.textContent = 'Niepoprawny adres e-mail.';
            isValid = false;
        }
        else
        {
            emailError.textContent = '';
        }

        const passwordPattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$/;
        if ( passwordValue.length < 8 )
        {
            passwordError.textContent = 'Hasło musi składać się z co najmniej 8 znaków.';
            isValid = false;
        }
        else if ( !/[A-Z]/.test( passwordValue ) )
        {
            passwordError.textContent = 'Hasło musi zawierać przynajmniej jedną wielką literę.';
            isValid = false;
        }
        else if ( !/[a-z]/.test( passwordValue ) )
        {
            passwordError.textContent = 'Hasło musi zawierać przynajmniej jedną małą literę.';
            isValid = false;
        }
        else if ( !/[!@#$%^&*]/.test( passwordValue ) )
        {
            passwordError.textContent = 'Hasło musi zawierać przynajmniej jeden znak specjalny.';
            isValid = false;
        }
        else if ( !/\d/.test( passwordValue ) )
        {
            passwordError.textContent = 'Hasło musi zawierać przynajmniej jedną cyfrę.';
            isValid = false;
        }
        else
        {
            passwordError.textContent = '';
        }

        if ( passwordValue !== confirmPasswordValue )
        {
            confirmPasswordError.textContent = 'Podane hasła nie pasują do siebie.';
            isValid = false;
        }
        else
        {
            confirmPasswordError.textContent = '';
        }

        if ( !isValid )
        {
            event.preventDefault();
        }
    });
});


const closeButton = document.querySelector( '.register-close-notification-button' );
const notificationContainer = document.querySelector( '.register-notyfication-container' );

closeButton.addEventListener( 'click', function()
{
    notificationContainer.style.display = 'none';
});
