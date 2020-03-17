//main libraries from Facebook used to build this app
const React = require('react');
const ReactDOM = require('react-dom');

//custom code that configures rest.js to include support for HAL, URI Templates, and other things.
//It also sets the default Accept request header to application/hal+json
const client = require('./client');

//creates a React component
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {categories: []};
	}

    //API invoked after React renders a component in the DOM
	componentDidMount() {
		client({method: 'GET', path: '/api/v1/categories'}).done(response => {
		    //State is data that the component is expected to handle itself
			this.setState({categories: response.entity._embedded.categories});
		});
	}

    //API that “draws” the component on the screen
	render() { (3)
		return (
			<CategoryList categories={this.state.categories}/>
		)
	}
}

class CategoryList extends React.Component{
	render() {
	//Properties encompass data that is passed into the component. Properties do NOT change but are instead fixed values
		const categories = this.props.categories.map(category =>
			<Category key={category._links.self.href} category={category}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Label</th>
					</tr>
					{categories}
				</tbody>
			</table>
		)
	}
}

class Category extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.category.label}</td>
			</tr>
		)
	}
}

//render accepts two arguments:
//a React component you defined as well as a DOM node to inject it into.
ReactDOM.render(
	<App />,
	document.getElementById('react')
)