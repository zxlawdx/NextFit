<div id="top" class="">

<div align="center" class="text-center">
<h1>NEXTFIT</h1>
<p><em>Empower Your Nutrition Journey with Intelligent Management</em></p>

<img alt="last-commit" src="https://img.shields.io/github/last-commit/zxlawdx/NextFit?style=flat&amp;logo=git&amp;logoColor=white&amp;color=0080ff" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="repo-top-language" src="https://img.shields.io/github/languages/top/zxlawdx/NextFit?style=flat&amp;color=0080ff" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="repo-language-count" src="https://img.shields.io/github/languages/count/zxlawdx/NextFit?style=flat&amp;color=0080ff" class="inline-block mx-1" style="margin: 0px 2px;">
<p><em>Built with the tools and technologies:</em></p>
<img alt="Ollama" src="https://img.shields.io/badge/Ollama-000000.svg?style=flat&amp;logo=Ollama&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="Org" src="https://img.shields.io/badge/Org-77AA99.svg?style=flat&amp;logo=Org&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="FastAPI" src="https://img.shields.io/badge/FastAPI-009688.svg?style=flat&amp;logo=FastAPI&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="Gradle" src="https://img.shields.io/badge/Gradle-02303A.svg?style=flat&amp;logo=Gradle&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="ReadMe" src="https://img.shields.io/badge/ReadMe-018EF5.svg?style=flat&amp;logo=ReadMe&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<br>
<img alt="Docker" src="https://img.shields.io/badge/Docker-2496ED.svg?style=flat&amp;logo=Docker&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="XML" src="https://img.shields.io/badge/XML-005FAD.svg?style=flat&amp;logo=XML&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="Python" src="https://img.shields.io/badge/Python-3776AB.svg?style=flat&amp;logo=Python&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="Google" src="https://img.shields.io/badge/Google-4285F4.svg?style=flat&amp;logo=Google&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
<img alt="bat" src="https://img.shields.io/badge/bat-31369E.svg?style=flat&amp;logo=bat&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
</div>
<br>
<hr>
<h2>Table of Contents</h2>
<ul class="list-disc pl-4 my-0">
<li class="my-0"><a href="#overview">Overview</a></li>
<li class="my-0"><a href="#getting-started">Getting Started</a>
<ul class="list-disc pl-4 my-0">
<li class="my-0"><a href="#prerequisites">Prerequisites</a></li>
<li class="my-0"><a href="#installation">Installation</a></li>
<li class="my-0"><a href="#usage">Usage</a></li>
<li class="my-0"><a href="#testing">Testing</a></li>
</ul>
</li>
</ul>
<hr>
<h2>Overview</h2>
<p>NextFit is a powerful desktop application designed to streamline food management and dietary tracking, leveraging JavaFX for a modern user experience.</p>
<p><strong>Why NextFit?</strong></p>
<p>This project empowers users to take control of their nutrition with an intuitive interface and advanced AI integration. The core features include:</p>
<ul class="list-disc pl-4 my-0">
<li class="my-0">🥗 <strong>AI Interaction:</strong> Seamlessly communicate with an AI model for personalized dietary advice.</li>
<li class="my-0">📊 <strong>Meal Management:</strong> User-friendly interface for tracking meals and nutritional information.</li>
<li class="my-0">🔒 <strong>User Authentication:</strong> Secure login and registration with email verification for enhanced security.</li>
<li class="my-0">📈 <strong>Data Visualization:</strong> Interactive charts to visualize nutrient consumption over time.</li>
<li class="my-0">🐳 <strong>Containerization:</strong> Simplified deployment using Docker for both JavaFX and FastAPI components.</li>
<li class="my-0">🗄️ <strong>Local Database:</strong> Efficient local data storage with SQLite for quick access and management.</li>
</ul>
<hr>
<h2>Getting Started</h2>
<h3>Prerequisites</h3>
<p>This project requires the following dependencies:</p>
<ul class="list-disc pl-4 my-0">
<li class="my-0"><strong>Programming Language:</strong> unknown</li>
<li class="my-0"><strong>Package Manager:</strong> Gradle, Pip, Maven</li>
<li class="my-0"><strong>Container Runtime:</strong> Docker</li>
</ul>
<h3>Installation</h3>
<p>Build NextFit from the source and intsall dependencies:</p>
<ol>
<li class="my-0">
<p><strong>Clone the repository:</strong></p>
<pre><code class="language-sh">❯ git clone https://github.com/zxlawdx/NextFit
</code></pre>
</li>
<li class="my-0">
<p><strong>Navigate to the project directory:</strong></p>
<pre><code class="language-sh">❯ cd NextFit
</code></pre>
</li>
<li class="my-0">
<p><strong>Install the dependencies:</strong></p>
</li>
</ol>
<p><strong>Using <a href="https://www.docker.com/">docker</a>:</strong></p>
<pre><code class="language-sh">❯ docker build -t zxlawdx/NextFit .
</code></pre>
<p><strong>Using <a href="https://gradle.org/">gradle</a>:</strong></p>
<pre><code class="language-sh">❯ gradle build
</code></pre>
<p><strong>Using <a href="https://pypi.org/project/pip/">pip</a>:</strong></p>
<pre><code class="language-sh">❯ pip install -r requirements.txt
</code></pre>
<p><strong>Using <a href="https://maven.apache.org/">maven</a>:</strong></p>
<pre><code class="language-sh">❯ mvn install
</code></pre>
<h3>Usage</h3>
<p>Run the project with:</p>
<p><strong>Using <a href="https://www.docker.com/">docker</a>:</strong></p>
<pre><code class="language-sh">docker run -it {image_name}
</code></pre>
<p><strong>Using <a href="https://gradle.org/">gradle</a>:</strong></p>
<pre><code class="language-sh">gradle run
</code></pre>
<p><strong>Using <a href="https://pypi.org/project/pip/">pip</a>:</strong></p>
<pre><code class="language-sh">python {entrypoint}
</code></pre>
<p><strong>Using <a href="https://maven.apache.org/">maven</a>:</strong></p>
<pre><code class="language-sh">mvn exec:java
</code></pre>
<h3>Testing</h3>
<p>Nextfit uses the {<strong>test_framework</strong>} test framework. Run the test suite with:</p>
<p><strong>Using <a href="https://www.docker.com/">docker</a>:</strong></p>
<pre><code class="language-sh">echo 'INSERT-TEST-COMMAND-HERE'
</code></pre>
<p><strong>Using <a href="https://gradle.org/">gradle</a>:</strong></p>
<pre><code class="language-sh">gradle test
</code></pre>
<p><strong>Using <a href="https://pypi.org/project/pip/">pip</a>:</strong></p>
<pre><code class="language-sh">pytest
</code></pre>
<p><strong>Using <a href="https://maven.apache.org/">maven</a>:</strong></p>
<pre><code class="language-sh">mvn test
</code></pre>
<hr>
<div align="left" class=""><a href="#top">⬆ Return</a></div>
<hr></div>

