<!-- src/views/TestExam.vue -->
<template>
  <div class="test-exam">
    <!-- Tiêu đề bài thi -->
    <h1 class="test-title">2024 Practice Set TOEIC 1</h1>

    <!-- Tabs Part 1-7 -->
    <div class="part-tabs">
      <button
        v-for="part in 7"
        :key="part"
        :class="{ active: currentPart === part }"
        @click="switchPart(part)"
      >
        Part {{ part }}
      </button>
    </div>

    <!-- Nội dung câu hỏi -->
    <div class="test-content">
      <div class="questions">
        <!-- Part 1, 2, 5: Hiển thị từng câu hỏi riêng lẻ -->
        <div v-if="![3, 4, 6, 7].includes(currentPart)">
          <QuestionBlock
            v-for="(question, index) in filteredQuestions"
            :key="question.id"
            :ref="'question-' + question.id"
            :question-id="question.id"
            :image="question.image"
            :audio="question.audio"
            :content="question.content"
            :options="question.options"
            :is-last-question="index === filteredQuestions.length - 1"
            @select-option="handleOptionSelect"
          />
        </div>

        <!-- Part 3 và Part 4: Hiển thị theo nhóm 3 câu hỏi -->
        <div v-else-if="[3, 4].includes(currentPart)">
          <div
            v-for="group in questionGroups"
            :key="group.groupId"
            class="question-group"
            :ref="'group-' + group.groupId"
          >
            <!-- File nghe cho cả nhóm -->
            <ListeningPlayer :audio-source="group.audio" />

            <!-- Bọc hình ảnh và câu hỏi trong một div flex -->
            <div class="group-content">
              <!-- Hiển thị hình ảnh cho nhóm (nếu có) -->
              <img
                v-if="group.image"
                :src="group.image"
                alt="Group Image"
                class="group-image"
              />

              <!-- Các câu hỏi trong nhóm -->
              <div class="group-questions">
                <QuestionBlock
                  v-for="(question, index) in group.questions"
                  :key="question.id"
                  :ref="'question-' + question.id"
                  :question-id="question.id"
                  :image="null"
                  :audio="null"
                  :content="question.content"
                  :options="question.options"
                  :is-last-question="index === group.questions.length - 1"
                  @select-option="handleOptionSelect"
                />
              </div>
            </div>
          </div>
        </div>

        <!-- Part 6: Hiển thị theo nhóm 4 câu hỏi -->
        <div v-else-if="currentPart === 6">
          <div
            v-for="group in questionGroups"
            :key="group.groupId"
            class="question-group"
            :ref="'group-' + group.groupId"
          >
            <!-- Bọc đoạn văn/hình ảnh và câu hỏi trong một div flex -->
            <div class="group-content">
              <!-- Hiển thị đoạn văn ngắn hoặc hình ảnh -->
              <div class="group-passage">
                <ReadingPassage :content="group.passage" :image="group.image" />
              </div>

              <!-- Các câu hỏi trong nhóm -->
              <div class="group-questions">
                <QuestionBlock
                  v-for="(question, index) in group.questions"
                  :key="question.id"
                  :ref="'question-' + question.id"
                  :question-id="question.id"
                  :image="null"
                  :audio="null"
                  :content="question.content"
                  :options="question.options"
                  :is-last-question="index === group.questions.length - 1"
                  @select-option="handleOptionSelect"
                />
              </div>
            </div>
          </div>
        </div>

        <!-- Part 7: Hiển thị Single, Double, Triple Passage -->
        <div v-else>
          <div
            v-for="group in questionGroups"
            :key="group.groupId"
            class="question-group"
            :ref="'group-' + group.groupId"
          >
            <!-- Bọc đoạn văn/hình ảnh và câu hỏi trong một div flex -->
            <div class="group-content">
              <!-- Hiển thị các đoạn văn -->
              <div class="group-passage">
                <!-- Single Passage -->
                <ReadingPassage
                  v-if="group.type === 'single'"
                  :content="null"
                  :image="group.image"
                />
                <!-- Double Passage -->
                <div v-else-if="group.type === 'double'" class="double-passage">
                  <ReadingPassage :content="null" :image="group.images[0]" />
                  <ReadingPassage :content="null" :image="group.images[1]" />
                </div>
                <!-- Triple Passage -->
                <div v-else-if="group.type === 'triple'" class="triple-passage">
                  <ReadingPassage :content="null" :image="group.images[0]" />
                  <ReadingPassage :content="null" :image="group.images[1]" />
                  <ReadingPassage :content="null" :image="group.images[2]" />
                </div>
              </div>

              <!-- Các câu hỏi trong nhóm -->
              <div class="group-questions">
                <QuestionBlock
                  v-for="(question, index) in group.questions"
                  :key="question.id"
                  :ref="'question-' + question.id"
                  :question-id="question.id"
                  :image="null"
                  :audio="null"
                  :content="question.content"
                  :options="question.options"
                  :is-last-question="index === group.questions.length - 1"
                  @select-option="handleOptionSelect"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Thanh điều hướng câu hỏi -->
      <div class="navigator-wrapper">
        <PartNavigator
          :questions="questions"
          :current-question-id="currentQuestionId"
          @navigate="navigateToQuestion"
        />
        <div class="navigation-buttons">
          <button @click="prevPart" :disabled="currentPart === 1">
            Phần trước
          </button>
          <button @click="nextPart" :disabled="currentPart === 7">
            Phần sau
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import QuestionBlock from "@/components/exams/QuestionBlock.vue";
import ReadingPassage from "@/components/exams/ReadingPassage.vue";
import PartNavigator from "@/components/exams/PartNavigator.vue";
import ListeningPlayer from "@/components/exams/ListeningPlayer.vue";
import { useExamStore } from "@/store/useExamStore";

export default {
  components: {
    QuestionBlock,
    ReadingPassage,
    PartNavigator,
    ListeningPlayer,
  },
  setup() {
    const examStore = useExamStore();
    return { examStore };
  },
  data() {
    return {
      currentPart: 1,
      currentQuestionId: 1,
      remainingTime: 7200,
      questions: [
        // Part 1: 6 câu hỏi (Listening - Photo)
        ...Array.from({ length: 6 }, (_, index) => ({
          id: index + 1,
          part: 1,
          image: `https://picsum.photos/500/300?random=${index + 1}`,
          audio: `/assets/audio/part1_question${index + 1}.mp3`,
          content: `Describe the photo ${index + 1}`,
          options: [
            { value: "A", text: "Option A for photo " + (index + 1) },
            { value: "B", text: "Option B for photo " + (index + 1) },
            { value: "C", text: "Option C for photo " + (index + 1) },
            { value: "D", text: "Option D for photo " + (index + 1) },
          ],
          answered: false,
        })),
        // Part 2: 25 câu hỏi (Listening - Question-Response)
        ...Array.from({ length: 25 }, (_, index) => ({
          id: index + 7,
          part: 2,
          image: null,
          audio: `/assets/audio/part2_question${index + 1}.mp3`,
          content: `Question ${index + 7}: What is the response?`,
          options: [
            { value: "A", text: "Response A for question " + (index + 7) },
            { value: "B", text: "Response B for question " + (index + 7) },
            { value: "C", text: "Response C for question " + (index + 7) },
            { value: "D", text: "Response D for question " + (index + 7) },
          ],
          answered: false,
        })),
        // Part 3: 39 câu hỏi (Listening - Short Conversations, nhóm 3 câu 1 đoạn)
        ...Array.from({ length: 13 }, (_, groupIndex) => {
          const groupId = groupIndex + 1;
          return Array.from({ length: 3 }, (_, questionIndex) => {
            const id = groupIndex * 3 + questionIndex + 32;
            let content = "";
            let options = [];
            if (groupIndex === 10) {
              if (questionIndex === 0) {
                content =
                  "According to the man, why is today’s Sea-Ride Special tour popular?";
                options = [
                  {
                    value: "A",
                    text: "It offers a chance to see migrating birds.",
                  },
                  {
                    value: "B",
                    text: "Water conditions are likely to be favorable.",
                  },
                  { value: "C", text: "A guest chef is preparing lunch." },
                  {
                    value: "D",
                    text: "Someone special will be guiding the tour.",
                  },
                ];
              } else if (questionIndex === 1) {
                content =
                  "Look at the graphic. What time will the woman depart on a tour?";
                options = [
                  { value: "A", text: "At 9" },
                  { value: "B", text: "At 10" },
                  { value: "C", text: "At 11" },
                  { value: "D", text: "At 12" },
                ];
              } else {
                content = "What will the woman most likely do next?";
                options = [
                  { value: "A", text: "Return to her hotel" },
                  { value: "B", text: "Visit a cafe" },
                  { value: "C", text: "Call a friend" },
                  { value: "D", text: "Store her bags in a locker" },
                ];
              }
            } else {
              if (questionIndex === 0) {
                content = "Why is the woman calling?";
                options = [
                  { value: "A", text: "To request a ticket change" },
                  { value: "B", text: "To make a dinner reservation" },
                  { value: "C", text: "To order merchandise" },
                  { value: "D", text: "To plan a vacation" },
                ];
              } else if (questionIndex === 1) {
                content = "Why does the man apologize?";
                options = [
                  { value: "A", text: "An event was canceled" },
                  { value: "B", text: "A line is very long" },
                  { value: "C", text: "A payment option is unavailable" },
                  { value: "D", text: "A computer program is not working" },
                ];
              } else {
                content = "What does the man remind the woman about?";
                options = [
                  { value: "A", text: "A meal voucher" },
                  { value: "B", text: "Some free souvenirs" },
                  { value: "C", text: "An increase in price" },
                  { value: "D", text: "A refund policy" },
                ];
              }
            }
            return {
              id,
              part: 3,
              groupId: `part3_group${groupId}`,
              audio: `/assets/audio/part3_conversation${groupId}.mp3`,
              content,
              options,
              answered: false,
            };
          });
        }).flat(),
        // Part 4: 30 câu hỏi (Listening - Short Talks, nhóm 3 câu 1 đoạn)
        ...Array.from({ length: 10 }, (_, groupIndex) => {
          const groupId = groupIndex + 1;
          return Array.from({ length: 3 }, (_, questionIndex) => {
            const id = groupIndex * 3 + questionIndex + 71;
            let content = "";
            let options = [];
            if (groupIndex === 0) {
              if (questionIndex === 0) {
                content = "What is the main topic of the talk?";
                options = [
                  { value: "A", text: "A new company policy" },
                  { value: "B", text: "A product launch event" },
                  { value: "C", text: "A staff training session" },
                  { value: "D", text: "A company merger" },
                ];
              } else if (questionIndex === 1) {
                content = "Who is the speaker addressing?";
                options = [
                  { value: "A", text: "New employees" },
                  { value: "B", text: "Senior managers" },
                  { value: "C", text: "Clients" },
                  { value: "D", text: "Shareholders" },
                ];
              } else {
                content = "What will happen next week?";
                options = [
                  { value: "A", text: "A company-wide meeting" },
                  { value: "B", text: "A product demonstration" },
                  { value: "C", text: "A team-building event" },
                  { value: "D", text: "A performance review" },
                ];
              }
            } else {
              if (questionIndex === 0) {
                content = `Question ${id}: What is the purpose of the talk?`;
                options = [
                  { value: "A", text: "To announce a schedule change" },
                  { value: "B", text: "To introduce a new team member" },
                  { value: "C", text: "To explain a project update" },
                  { value: "D", text: "To discuss a budget plan" },
                ];
              } else if (questionIndex === 1) {
                content = `Question ${id}: Who is the intended audience?`;
                options = [
                  { value: "A", text: "Employees" },
                  { value: "B", text: "Customers" },
                  { value: "C", text: "Suppliers" },
                  { value: "D", text: "Investors" },
                ];
              } else {
                content = `Question ${id}: What does the speaker suggest?`;
                options = [
                  { value: "A", text: "Arriving early" },
                  { value: "B", text: "Submitting a report" },
                  { value: "C", text: "Attending a workshop" },
                  { value: "D", text: "Contacting a manager" },
                ];
              }
            }
            return {
              id,
              part: 4,
              groupId: `part4_group${groupId}`,
              audio: `/assets/audio/part4_talk${groupId}.mp3`,
              content,
              options,
              answered: false,
            };
          });
        }).flat(),
        // Part 5: 30 câu hỏi (Reading - Incomplete Sentences)
        ...Array.from({ length: 30 }, (_, index) => {
          const id = index + 101;
          let content = "";
          let options = [];
          if (id === 101) {
            content =
              "When she held her last meeting, Ms. Toba ___ her sales staff to perform even better next quarter.";
            options = [
              { value: "A", text: "encourage" },
              { value: "B", text: "is encouraging" },
              { value: "C", text: "encouraged" },
              { value: "D", text: "was encouraged" },
            ];
          } else if (id === 102) {
            content =
              "All staff have been informed ___ the proposed partnership with ERI Finance.";
            options = [
              { value: "A", text: "for" },
              { value: "B", text: "about" },
              { value: "C", text: "to" },
              { value: "D", text: "at" },
            ];
          } else if (id === 103) {
            content =
              "On Friday, Mr. Nakamura will discuss ___ ideas for supporting busy waiters.";
            options = [
              { value: "A", text: "his" },
              { value: "B", text: "him" },
              { value: "C", text: "himself" },
              { value: "D", text: "he" },
            ];
          } else {
            content = `Question ${id}: Complete the sentence.`;
            options = [
              { value: "A", text: "Option A for question " + id },
              { value: "B", text: "Option B for question " + id },
              { value: "C", text: "Option C for question " + id },
              { value: "D", text: "Option D for question " + id },
            ];
          }
          return {
            id,
            part: 5,
            image: null,
            audio: null,
            content,
            options,
            answered: false,
          };
        }),
        // Part 6: 16 câu hỏi (Reading - Text Completion, nhóm 4 câu 1 đoạn)
        ...Array.from({ length: 4 }, (_, groupIndex) => {
          const groupId = groupIndex + 1;
          return Array.from({ length: 4 }, (_, questionIndex) => {
            const id = groupIndex * 4 + questionIndex + 131;
            let content = "";
            let options = [];

            if (groupIndex === 0) {
              if (questionIndex === 0) {
                content = "Question 131: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 131" },
                  { value: "B", text: "Option B for question 131" },
                  { value: "C", text: "Option C for question 131" },
                  { value: "D", text: "Option D for question 131" },
                ];
              } else if (questionIndex === 1) {
                content = "Question 132: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 132" },
                  { value: "B", text: "Option B for question 132" },
                  { value: "C", text: "Option C for question 132" },
                  { value: "D", text: "Option D for question 132" },
                ];
              } else if (questionIndex === 2) {
                content = "Question 133: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 133" },
                  { value: "B", text: "Option B for question 133" },
                  { value: "C", text: "Option C for question 133" },
                  { value: "D", text: "Option D for question 133" },
                ];
              } else {
                content = "Question 134: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 134" },
                  { value: "B", text: "Option B for question 134" },
                  { value: "C", text: "Option C for question 134" },
                  { value: "D", text: "Option D for question 134" },
                ];
              }
            } else if (groupIndex === 1) {
              if (questionIndex === 0) {
                content = "Question 135: Complete the text.";
                options = [
                  { value: "A", text: "economies" },
                  { value: "B", text: "economize" },
                  { value: "C", text: "economy" },
                  { value: "D", text: "economical" },
                ];
              } else if (questionIndex === 1) {
                content = "Question 136: Complete the text.";
                options = [
                  { value: "A", text: "below" },
                  { value: "B", text: "finally" },
                  { value: "C", text: "sometimes" },
                  { value: "D", text: "hourly" },
                ];
              } else if (questionIndex === 2) {
                content = "Question 137: Complete the text.";
                options = [
                  { value: "A", text: "Come again very soon." },
                  { value: "B", text: "It is warmer in the store." },
                  { value: "C", text: "Do not take it inside." },
                  { value: "D", text: "The tank is prefilled." },
                ];
              } else {
                content = "Question 138: Complete the text.";
                options = [
                  { value: "A", text: "model" },
                  { value: "B", text: "version" },
                  { value: "C", text: "heater" },
                  { value: "D", text: "replacement" },
                ];
              }
            } else if (groupIndex === 2) {
              if (questionIndex === 0) {
                content = "Question 139: Complete the text.";
                options = [
                  { value: "A", text: "experience" },
                  { value: "B", text: "experienced" },
                  { value: "C", text: "experiencing" },
                  { value: "D", text: "experiential" },
                ];
              } else if (questionIndex === 1) {
                content = "Question 140: Complete the text.";
                options = [
                  { value: "A", text: "either" },
                  { value: "B", text: "both" },
                  { value: "C", text: "rather" },
                  { value: "D", text: "each" },
                ];
              } else if (questionIndex === 2) {
                content = "Question 141: Complete the text.";
                options = [
                  { value: "A", text: "Thereford" },
                  { value: "B", text: "Reckless" },
                  { value: "C", text: "For example" },
                  { value: "D", text: "Moreover" },
                ];
              } else {
                content = "Question 142: Complete the text.";
                options = [
                  {
                    value: "A",
                    text: "We also need to inform you that your payment is five days past due.",
                  },
                  {
                    value: "B",
                    text: "We request that you purchase all related accessories in our retail store.",
                  },
                  {
                    value: "C",
                    text: "If you get an error message, disconnect from the Internet and try again.",
                  },
                  {
                    value: "D",
                    text: "If you cannot find what you need online, simply call our support number.",
                  },
                ];
              }
            } else {
              if (questionIndex === 0) {
                content = "Question 143: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 143" },
                  { value: "B", text: "Option B for question 143" },
                  { value: "C", text: "Option C for question 143" },
                  { value: "D", text: "Option D for question 143" },
                ];
              } else if (questionIndex === 1) {
                content = "Question 144: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 144" },
                  { value: "B", text: "Option B for question 144" },
                  { value: "C", text: "Option C for question 144" },
                  { value: "D", text: "Option D for question 144" },
                ];
              } else if (questionIndex === 2) {
                content = "Question 145: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 145" },
                  { value: "B", text: "Option B for question 145" },
                  { value: "C", text: "Option C for question 145" },
                  { value: "D", text: "Option D for question 145" },
                ];
              } else {
                content = "Question 146: Complete the text.";
                options = [
                  { value: "A", text: "Option A for question 146" },
                  { value: "B", text: "Option B for question 146" },
                  { value: "C", text: "Option C for question 146" },
                  { value: "D", text: "Option D for question 146" },
                ];
              }
            }

            return {
              id,
              part: 6,
              groupId: `part6_group${groupId}`,
              content,
              options,
              answered: false,
              image: `https://picsum.photos/500/300?random=${groupId}`,
            };
          });
        }).flat(),
        // Part 7: 54 câu hỏi (Reading - Reading Comprehension)
        // Single Passages: 10 đoạn, 29 câu hỏi
        // Đoạn 1: 147–148 (2 câu)
        ...Array.from({ length: 2 }, (_, questionIndex) => {
          const id = 147 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single1",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single1`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 2: 149–150 (2 câu)
        ...Array.from({ length: 2 }, (_, questionIndex) => {
          const id = 149 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single2",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single2`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 3: 151–152 (2 câu)
        ...Array.from({ length: 2 }, (_, questionIndex) => {
          const id = 151 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single3",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single3`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 4: 153–154 (2 câu)
        ...Array.from({ length: 2 }, (_, questionIndex) => {
          const id = 153 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single4",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single4`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 5: 155–157 (3 câu)
        ...Array.from({ length: 3 }, (_, questionIndex) => {
          const id = 155 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single5",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single5`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 6: 158–160 (3 câu)
        ...Array.from({ length: 3 }, (_, questionIndex) => {
          const id = 158 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single6",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single6`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 7: 161–163 (3 câu)
        ...Array.from({ length: 3 }, (_, questionIndex) => {
          const id = 161 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single7",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single7`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 8: 164–167 (4 câu)
        ...Array.from({ length: 4 }, (_, questionIndex) => {
          const id = 164 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single8",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single8`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 9: 168–171 (4 câu)
        ...Array.from({ length: 4 }, (_, questionIndex) => {
          const id = 168 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single9",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single9`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Đoạn 10: 172–175 (4 câu)
        ...Array.from({ length: 4 }, (_, questionIndex) => {
          const id = 172 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_single10",
            type: "single",
            image: `https://picsum.photos/500/300?random=part7_single10`,
            audio: null,
            content: `Question ${id}: What is the main purpose of the passage?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Double Passages: 2 cặp, 10 câu hỏi
        // Nhóm 1: 176–180 (5 câu)
        ...Array.from({ length: 5 }, (_, questionIndex) => {
          const id = 176 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_double1",
            type: "double",
            images: [
              `https://picsum.photos/500/300?random=part7_double1_1`,
              `https://picsum.photos/500/300?random=part7_double1_2`,
            ],
            audio: null,
            content: `Question ${id}: What information is provided in both passages?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Nhóm 2: 181–185 (5 câu)
        ...Array.from({ length: 5 }, (_, questionIndex) => {
          const id = 181 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_double2",
            type: "double",
            images: [
              `https://picsum.photos/500/300?random=part7_double2_1`,
              `https://picsum.photos/500/300?random=part7_double2_2`,
            ],
            audio: null,
            content: `Question ${id}: What information is provided in both passages?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Triple Passages: 3 bộ, 15 câu hỏi
        // Nhóm 1: 186–190 (5 câu)
        ...Array.from({ length: 5 }, (_, questionIndex) => {
          const id = 186 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_triple1",
            type: "triple",
            images: [
              `https://picsum.photos/500/300?random=part7_triple1_1`,
              `https://picsum.photos/500/300?random=part7_triple1_2`,
              `https://picsum.photos/500/300?random=part7_triple1_3`,
            ],
            audio: null,
            content: `Question ${id}: What is the relationship between the three passages?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Nhóm 2: 191–195 (5 câu)
        ...Array.from({ length: 5 }, (_, questionIndex) => {
          const id = 191 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_triple2",
            type: "triple",
            images: [
              `https://picsum.photos/500/300?random=part7_triple2_1`,
              `https://picsum.photos/500/300?random=part7_triple2_2`,
              `https://picsum.photos/500/300?random=part7_triple2_3`,
            ],
            audio: null,
            content: `Question ${id}: What is the relationship between the three passages?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
        // Nhóm 3: 196–200 (5 câu)
        ...Array.from({ length: 5 }, (_, questionIndex) => {
          const id = 196 + questionIndex;
          return {
            id,
            part: 7,
            groupId: "part7_triple3",
            type: "triple",
            images: [
              `https://picsum.photos/500/300?random=part7_triple3_1`,
              `https://picsum.photos/500/300?random=part7_triple3_2`,
              `https://picsum.photos/500/300?random=part7_triple3_3`,
            ],
            audio: null,
            content: `Question ${id}: What is the relationship between the three passages?`,
            options: [
              { value: "A", text: `Option A for question ${id}` },
              { value: "B", text: `Option B for question ${id}` },
              { value: "C", text: `Option C for question ${id}` },
              { value: "D", text: `Option D for question ${id}` },
            ],
            answered: false,
          };
        }),
      ],
    };
  },
  computed: {
    filteredQuestions() {
      const filtered = this.questions.filter(
        (q) => q.part === this.currentPart
      );
      console.log("Filtered Questions:", filtered);
      return filtered;
    },
    questionGroups() {
      if (![3, 4, 6, 7].includes(this.currentPart)) return [];
      const groups = {};
      this.filteredQuestions.forEach((question) => {
        if (!groups[question.groupId]) {
          groups[question.groupId] = {
            groupId: question.groupId,
            audio: question.audio,
            passage: question.passage,
            image: question.image,
            images: question.images,
            type: question.type || "single",
            questions: [],
          };
          console.log(`Group ${question.groupId}:`, groups[question.groupId]); // Debug
        }
        groups[question.groupId].questions.push(question);
      });
      return Object.values(groups);
    },
  },
  watch: {
    currentQuestionId(newId) {
      this.$nextTick(() => {
        const questionElement = this.$refs[`question-${newId}`]?.[0];
        if (questionElement) {
          questionElement.$el.scrollIntoView({ behavior: "smooth" });
        }
      });
    },
  },
  created() {
    console.log("Questions:", this.questions);
    this.examStore.setQuestions(this.questions);
    this.currentQuestionId = this.filteredQuestions[0]?.id || 1;
  },
  methods: {
    switchPart(part) {
      this.currentPart = part;
      this.currentQuestionId = this.filteredQuestions[0]?.id || 1;
    },
    handleOptionSelect(questionId, optionValue) {
      this.examStore.setAnswer(questionId, optionValue);
      const question = this.questions.find((q) => q.id === questionId);
      if (question) question.answered = true;
    },
    navigateToQuestion(questionId) {
      const question = this.questions.find((q) => q.id === questionId);
      if (question) {
        this.currentPart = question.part;
        this.currentQuestionId = questionId;
      }
    },
    prevPart() {
      if (this.currentPart > 1) {
        this.currentPart--;
        this.currentQuestionId = this.filteredQuestions[0]?.id || 1;
      }
    },
    nextPart() {
      if (this.currentPart < 7) {
        this.currentPart++;
        this.currentQuestionId = this.filteredQuestions[0]?.id || 1;
      }
    },
    formatTime(seconds) {
      const minutes = Math.floor(seconds / 60);
      const secs = seconds % 60;
      return `${minutes}:${secs < 10 ? "0" : ""}${secs}`;
    },
  },
};
</script>

<style scoped>
.test-exam {
  padding: 20px;
  max-width: 1500px;
  margin: 0 auto;
  margin-top: 40px;
  font-family: Arial, sans-serif;
}

.test-title {
  font-size: 17px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
}

.part-tabs {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 20px;
}

.part-tabs button {
  width: 5%;
  padding: 4px 15px;
  background-color: #f8f9fa;
  border-radius: 25px;
  white-space: nowrap;
  color: #1a1a1a;
  border: 1px solid #1a1a1a;
  transition: all 0.3s;
}

.part-tabs button:hover {
  background: #007bff;
  color: white;
  border-color: #007bff;
}

.part-tabs button.active {
  background: #007bff;
  color: white;
  border-color: #007bff;
}

.test-content {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: flex-start;
}

.questions {
  width: 85%;
  background: #ffffff;
  padding: 20px;
}

.question-group {
  margin-bottom: 2rem;
  padding-bottom: 2rem;
  border-bottom: 2px solid #030303;
}
.question-group:last-child {
  border-bottom: none;
}

.group-content {
  display: flex;
  gap: 20px;
  align-items: flex-start;
}

.group-image {
  width: 100%;
  max-width: 650px;
  height: auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  object-fit: contain;
}

.group-passage {
  width: 100%;
  max-width: 650px;
}

.double-passage,
.triple-passage {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.group-questions {
  text-align: left;
  flex: 1;
}

.navigator-wrapper {
  width: 15%;
  display: flex;
  flex-direction: column;
  gap: 15px;
  position: sticky;
}

.navigation-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.navigation-buttons button {
  padding: 10px;
  border: none;
  background: #007bff;
  color: white;
  cursor: pointer;
  border-radius: 5px;
}

.navigation-buttons button:disabled {
  background: #ccc;
  cursor: not-allowed;
}
</style>
