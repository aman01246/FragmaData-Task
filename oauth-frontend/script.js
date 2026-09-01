const BACKEND_URL = "http://localhost:8080";


/* =========================
   SHOW PROVIDERS
========================= */

async function showProviders() {

  const oauthButton =
    document.getElementById("oauthButton");

  // Hide Continue with OAuth button
  if (oauthButton) {
    oauthButton.style.display = "none";
  }

  await loadProviders();
}


/* =========================
   LOAD OAUTH PROVIDERS
========================= */

async function loadProviders() {
  try {
    const response = await fetch(BACKEND_URL + "/api/auth/providers");

     console.log("Response status:", response.status);


    if (!response.ok) {
      throw new Error("Unable to load OAuth providers");
    }

    const providers = await response.json();

    console.log("Providers:", providers);

    const container = document.getElementById("providerContainer");

       // Clear container first
    container.innerHTML = "";


    providers.forEach((provider) => {
      const button = document.createElement("button");

      button.className = `oauth-button ${provider.id.toLowerCase()}-button`;

      button.textContent = provider.name;

      button.addEventListener("click", function () {
        login(provider.id);
      });

      container.appendChild(button);
    });
  } catch (error) {
    console.error(error);

    alert("Unable to load login providers");
  }
}

/* =========================
   LOGIN
========================= */

function login(providerName) {
  window.location.href = BACKEND_URL + "/api/auth/login/" + providerName;
}

/* =========================
   REGISTRATION
========================= */

const registrationForm = document.getElementById("registrationForm");

if (registrationForm) {
  registrationForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const phone = document.getElementById("phone").value;

    const department = document.getElementById("department").value;

    const designation = document.getElementById("designation").value;

    const response = await fetch(
      "http://localhost:8080/api/users/complete-profile",
      {
        method: "POST",

        headers: {
          "Content-Type": "application/json",
        },

        credentials: "include",

        body: JSON.stringify({
          phone: phone,

          department: department,

          designation: designation,
        }),
      },
    );

    const result = await response.json();

    console.log(result);

    if (response.ok) {
      alert(result.message);

      window.location.href = "profile.html";
    } else {
      alert(result.message || "Profile registration failed");
    }
  });
}

/* =========================
   PROFILE
========================= */

if (window.location.pathname.includes("profile.html")) {
  loadProfile();
}

async function loadProfile() {
  try {
    const response = await fetch("http://localhost:8080/api/users/profile", {
      method: "GET",

      credentials: "include",
    });

    const result = await response.json();

    console.log(result);

    if (!response.ok) {
      alert(result.message);

      return;
    }

    const user = result.data;

    document.getElementById("userName").textContent = user.name;

    document.getElementById("userEmail").textContent = user.email;

    document.getElementById("profilePicture").src = user.profilePicture;

    document.getElementById("phoneValue").textContent = user.phone;

    document.getElementById("departmentValue").textContent = user.department;

    document.getElementById("designationValue").textContent = user.designation;

    // ==============================
    // SHOW ALL CONNECTED PROVIDERS
    // ==============================

    const identitiesContainer = document.getElementById("identitiesContainer");

    identitiesContainer.innerHTML = "";

    if (user.identities && user.identities.length > 0) {
      user.identities.forEach((identity) => {
        const providerDiv = document.createElement("div");

        providerDiv.className = "provider-item";

        providerDiv.innerHTML = `
                    <div>
                        <strong>Provider:</strong>
                        ${identity.provider}
                    </div>

                    <div>
                        <strong>Provider ID:</strong>
                        ${identity.providerUserId}
                    </div>
                `;

        identitiesContainer.appendChild(providerDiv);
      });
    } else {
      identitiesContainer.innerHTML = "<span>No provider connected</span>";
    }
  } catch (error) {
    console.error(error);

    alert("Unable to load profile");
  }
}

/* =========================
   LOGOUT
========================= */

function logout() {
  localStorage.removeItem("userProfile");

  window.location.href = "index.html";
}
