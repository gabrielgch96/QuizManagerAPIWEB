import { Component, OnInit } from '@angular/core';
import { Question } from 'src/app/datamodel/question';
import { QuestionsService } from 'src/app/services/questions.service';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Topic } from 'src/app/datamodel/topic';
import { Option } from 'src/app/datamodel/option';
import { TopicService } from 'src/app/services/topic.service';
import { FormGroup, Validators, FormBuilder, FormArray } from '@angular/forms';
import { OptionService } from 'src/app/services/option.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-question-detail',
  templateUrl: './question-detail.component.html',
  styleUrls: ['./question-detail.component.css']
})
export class QuestionDetailComponent implements OnInit {

  question: Question;
  question_options: Option[];
  topics: Topic[];
  questionForm: FormGroup;
  private currentUser: User;

  constructor(private route: ActivatedRoute,
    private authService: AuthenticationService,
    private fb: FormBuilder,
    private optionService: OptionService,
    private questionService: QuestionsService,
    private topicService: TopicService,
    private location: Location) {
      this.authService.currentUser.subscribe(x => this.currentUser = x);
     }

  ngOnInit() {
    this.questionForm = this.fb.group({
      question: this.fb.group({
        id: [0],
        content: ['', Validators.required],
        topic: ['', Validators.required]
      }),
      options: this.fb.array([], [Validators.required])
    });
    this.getQuestion();
    this.topicService.getAllTopics().subscribe(topics => this.topics = topics);
  }

  get options() {
    return this.questionForm.get('options') as FormArray;
  }

  get content() {
    return this.questionForm.get('question').get('content');
  }

  get id() {
    return this.questionForm.get('question').get('id');
  }

  setQuestionValuesinForm() {
    this.content.setValue(this.question.content);
    this.topic.setValue(this.question.topic.name);
  }

  get topic() {
    return this.questionForm.get('question').get('topic');
  }

  getQuestion(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.questionService.getQuestion(id).subscribe(
      question => {
        this.question = question;
        this.id.setValue(this.question.id);
        this.content.setValue(this.question.content);
        this.topic.setValue(this.question.topic.name);
        this.getQuestionOptions(this.question.id);
      });
  }

  getQuestionOptions(question_id: number): void {
    this.optionService.searchOption(question_id).subscribe(
      options => {
        this.question_options = options;
        this.question_options.forEach(option_element => {
          let optionForm: FormGroup = this.fb.group({
            id: [option_element.id],
            text: [option_element.text, Validators.required],
            is_right: [option_element.is_right, Validators.required]
          });
          this.options.push(optionForm);
        });
      });
  }

  goBack(): void {
    this.location.back();
  }

  updateQuestion() {
    let topic: Topic = this.topics.filter(topic => topic.name == this.question.topic.name)[0];
    this.question.topic = topic;
    this.question.content = this.content.value;
    let data = {
      'question': this.question,
      'options': this.options.value
    };
    this.questionService.updateQuestionWithOptions(JSON.stringify(data)).subscribe(
      question => this.question = question
    );
    this.goBack();
  }

  addOption() {
    let optionForm: FormGroup = this.fb.group({
      id: [0],
      text: ['', Validators.required],
      is_right: [false, Validators.required]
    });
    this.options.push(optionForm);
  }

  removeOption(index: number) {
    this.options.removeAt(index);
  }

}
