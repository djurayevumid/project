class Footer extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `

  <footer class="py-5">
    <div class="row">
      <div class="col-3">
        <h5>STACK OVERFLOW</h5>
        <ul class="nav flex-column">
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Questions</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Jobs</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Developer Jobs Directory</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Salary Calculator</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Help</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Mobile</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Disable Responsiveness</a></li>
        </ul>
      </div>

      <div class="col-3">
        <h5>PRODUCTS</h5>
        <ul class="nav flex-column">
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Teams</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Talent</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Advertising</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Enterprise</a></li>
        </ul>
      </div>

      <div class="col-3">
        <h5>COMPANY</h5>
        <ul class="nav flex-column">
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">About</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Press</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Work Here</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Privacy Policy</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Terms of Service</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Contact Us</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Cookie Settings</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Cookie Policy</a></li>
        </ul>
      </div>
      <div class="col-3">
        <h5>STACK EXCHANGE NETWORK</h5>
        <ul class="nav flex-column">
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Technology</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Culture & recreation</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Life & arts</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Science</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Professional</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Business</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">API</a></li>
          <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Data</a></li>
        </ul>
      </div>
    </div>

    <div class="d-flex justify-content-center mt-3">
      <p>© 2021 Kata Academy, Inc. All rights reserved.</p>
    </div>
  </footer>

        `
    }
}

customElements.define('jm-footer', Footer)